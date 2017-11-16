package pr0.ves.eliteboy.server

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import pr0.ves.eliteboy.elitedangerous.journal.Journal
import java.io.File
import java.nio.charset.Charset


class Settings {
    var journalFolder: String? = null
    var lastCMDR: String? = null
    var dbNeedsInitialization = true
    var commanders = ArrayList<String>()

    fun toFile() {
        val file = File(System.getProperty("user.dir") + "/store/", "settings.json")
        file.parentFile.mkdirs()
        file.createNewFile()
        val json = GsonBuilder().setPrettyPrinting().create().toJson(this)
        file.outputStream().write(json.toByteArray(Charset.forName("UTF-8")))
    }

    companion object {
        fun fromFile(): Settings {
            val file = File(System.getProperty("user.dir"), "/store/settings.json")
            return if (file.exists()) {
                Gson().fromJson<Settings>(file.bufferedReader().readText(), Settings::class.java).also {
                    if (!it.journalFolder.isNullOrEmpty()) {
                        Journal.setFDDIR(it.journalFolder!!)
                    }
                }
            } else {
                return Settings()
            }
        }
    }
}