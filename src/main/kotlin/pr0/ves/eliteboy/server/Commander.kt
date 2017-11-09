package pr0.ves.eliteboy.server

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import pr0.ves.eliteboy.elitedangerous.companionapi.Credentials
import pr0.ves.eliteboy.server.services.CommanderService
import java.io.File
import java.nio.charset.Charset
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Phaser
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread
import kotlin.system.measureTimeMillis

class Commander(var name: String = "") {
    @Transient
    var restApiToken = ""
    var restApiPassword = ""

    var credentials: Credentials? = null

    var edsmApiKey = ""
    var edsmCommander = ""

    fun usingEDSM() = !edsmApiKey.isEmpty()

    @Transient
    var runningLatch = CountDownLatch(0)
    @Transient
    lateinit var phaser: Phaser

    fun runLoop(timeout: Long, name: String, block: (cancel: () -> Unit) -> Unit) {
        phaser.register()
        thread(name = "${this.name}: $name") {
            try {
                var cancelled = false
                while (!cancelled && isRunning()) {
                    val time = measureTimeMillis {
                        try {
                            block({ cancelled = true })
                        } catch (t: Throwable) {
                            t.printStackTrace()
                        }
                    }

                    if (cancelled) continue

                    val sleep = timeout - time
                    if (sleep > 0) {
                        runningLatch.await(sleep, TimeUnit.MILLISECONDS)
                    }
                }
            } finally {
                phaser.arriveAndDeregister()
            }
        }
    }

    fun isRunning(): Boolean {
        return runningLatch.count > 0
    }

    fun task(task: Task, service: CommanderService) {
        task.run(this, service)
    }

    fun toFile() {
        val file = File(System.getProperty("user.dir") + "/store", "$name.json")
        file.parentFile.mkdirs()
        file.createNewFile()
        val json = GsonBuilder().setPrettyPrinting().create().toJson(this)
        file.outputStream().write(json.toByteArray(Charset.forName("UTF-8")))
    }

    companion object {
        fun fromFile(commander: String = ""): Commander {
            return if (commander != "") {
                val file = File(System.getProperty("user.dir"), "/store/$commander.json")
                if (file.exists()) {
                    Gson().fromJson<Commander>(file.bufferedReader().readText(), Commander::class.java).also {
                        it.name = commander
                        if (it.credentials != null)
                            it.credentials!!.updateCrypter()
                    }

                } else {
                    Commander(commander)
                }
            } else {
                Commander(commander)
            }
        }
    }

}