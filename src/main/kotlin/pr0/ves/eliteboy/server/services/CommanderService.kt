package pr0.ves.eliteboy.server.services

import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service
import pr0.ves.eliteboy.elitedangerous.companionapi.Credentials
import pr0.ves.eliteboy.elitedangerous.companionapi.EDCompanionApi
import pr0.ves.eliteboy.elitedangerous.companionapi.EDCompanionApi.State.*
import pr0.ves.eliteboy.elitedangerous.eddn.EDDNApi
import pr0.ves.eliteboy.elitedangerous.edsm.EDSMApi
import pr0.ves.eliteboy.elitedangerous.journal.events.LoadGame
import pr0.ves.eliteboy.server.Commander
import pr0.ves.eliteboy.server.Settings
import pr0.ves.eliteboy.server.controllers.CommanderController
import pr0.ves.eliteboy.server.db.DB
import pr0.ves.eliteboy.server.tasks.JournalWatcher
import java.io.File
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Phaser
import javax.annotation.PostConstruct

@Service
class CommanderService {
    final var settings = Settings.fromFile()

    @Bean
    fun settings() = settings

    final var commander = Commander.fromFile(settings.lastCMDR!!)

    final var edApi: EDCompanionApi? = null
    final var edsmApi = EDSMApi()
    final var eddnApi = EDDNApi(commander.name)

    init {
        if (commander.credentials != null) {
            edApi = EDCompanionApi(commander)
            edApi!!.login()
            eddnApi.edApi = edApi
        }
        if (commander.usingEDSM()) {
            edsmApi.apiKey = commander.edsmApiKey
            edsmApi.commander = commander.edsmCommander
        }
    }

    @Autowired
    lateinit var controller: CommanderController

    @Autowired
    lateinit var db: DB

    @Synchronized
    fun start() {
        if (commander.isRunning()) return

        val journalWatcher = JournalWatcher()
        journalWatcher.listeners.add(db)
        journalWatcher.listeners.add(controller)
        journalWatcher.listeners.add(edsmApi)
        journalWatcher.listeners.add(eddnApi)

        commander.runningLatch = CountDownLatch(1)
        commander.phaser = Phaser(1)


        commander.task(journalWatcher, this)
    }

    fun load(cmdr: String) {
        commander.toFile()
        commander = Commander.fromFile(cmdr)
    }


    fun loginEDApi(email: String = "", password: String = ""): Boolean {

        if (email.isEmpty() || password.isEmpty()) {
            if (commander.credentials == null) {
                return false
            }
        } else {
            if (commander.credentials == null) {
                commander.credentials = Credentials().also {
                    it.initializeCrypter()
                    it.email = email
                    it.setPassword(password)
                }
            } else {
                val creds = commander.credentials
                if (email != creds!!.email && password != creds.pwd()) {
                    logger.info { "Updating credentials" }
                    creds.email = email
                    creds.setPassword(password)
                }
            }
        }
        commander.toFile()
        if (edApi == null)
            edApi = EDCompanionApi(commander)
        return when (edApi!!.currentState) {
            NEEDS_LOGIN -> {
                edApi!!.login()
                true
            }
            NEEDS_CONFIRMATION -> {
                false
            }
            READY -> {
                false
            }
        }
    }


    @PostConstruct
    fun initializeDB() {
        if (settings.dbNeedsInitialization)
            db.populateDB()
        else {
            File(System.getProperty("user.dir"), "/store/entries.db").also {
                if (!it.exists()) {
                    it.parentFile.mkdirs()
                    it.createNewFile()
                    settings.dbNeedsInitialization = true
                    db.populateDB()
                }
            }
        }
        updateCommanders()
    }

    fun updateCommanders() {
        val cmdrs = ArrayList<String>()
        db.entryRepo.findDinstinctByEvent("LoadGame").forEach {
            val cmdr = (it as LoadGame).Commander!!
            if (!cmdrs.contains(cmdr))
                cmdrs.add(cmdr)
        }
        settings.commanders = cmdrs
        settings.toFile()
    }

    @PostConstruct
    fun autoStart() {
        if (commander.restApiPassword.isNotEmpty()) {
            logger.info { "Starting CMDR ${commander.name}" }
            start()
        }
    }

    companion object : KLogging()
}