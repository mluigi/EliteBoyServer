package pr0.ves.eliteboy.server.tasks

import mu.KLogging
import org.springframework.stereotype.Component
import pr0.ves.eliteboy.elitedangerous.journal.Journal
import pr0.ves.eliteboy.server.Commander
import pr0.ves.eliteboy.server.Task
import pr0.ves.eliteboy.server.listeners.JournalWatcherListener
import pr0.ves.eliteboy.server.services.CommanderService
import java.nio.file.FileSystems
import java.nio.file.Path
import java.nio.file.StandardWatchEventKinds

@Component
class JournalWatcher : Task {
    private final val watcher = FileSystems.getDefault().newWatchService()
    private val journals = mutableMapOf<String, Journal>()
    val listeners = ArrayList<JournalWatcherListener>()

    init {
        Journal.FDDIR.toPath().register(watcher, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY)
    }


    override fun run(cmdr: Commander, service: CommanderService) {
        initialCheck(service)
        cmdr.runLoop(750, name = "JournalWatcher") { cancel ->
            val key = watcher.poll()

            if (key != null) {
                if (key.isValid) {
                    key.pollEvents().forEach {
                        when {
                            it.kind() == StandardWatchEventKinds.ENTRY_CREATE -> {
                                val filename = (it.context() as Path).toString()
                                journals[filename] = Journal(filename)
                                service.db.journalRepo.save(journals[filename])
                            }
                            it.kind() == StandardWatchEventKinds.ENTRY_MODIFY -> {
                                val filename = (it.context() as Path).toString()
                                if (!journals.containsKey(filename)) {
                                    if (service.db.journalRepo.existsByFilename(filename))
                                        journals[filename] = service.db.journalRepo.findByFilename(filename)
                                    else {
                                        journals[filename] = Journal(filename)
                                        service.db.journalRepo.save(journals[filename])
                                    }
                                }
                                val newEntries = journals[filename]!!.readJournal()
                                journals.entries.removeIf { it.key != filename }
                                listeners.forEach {
                                    it.getEntries(newEntries)
                                }
                            }
                        }
                    }
                }
                if (!key.reset()) {
                    logger.error { "Exiting from journal watcher" }
                    cancel()
                }
            } else {
                journals.values.forEach {
                    it.journal.length()
                }
            }
        }
    }

    fun initialCheck(service: CommanderService) {
        val lastJournal = service.db.journalRepo.findFirstByOrderByIdDesc()
        val journalList = ArrayList<Journal>()
        Journal.FDDIR.listFiles()
                .filter { it.isFile && it.name.startsWith("Journal", true) }
                .forEach {
                    journalList.add(Journal(it.name))
                }

        journalList.removeIf {
            it.dateTime().isBefore(lastJournal.dateTime())
        }

        journalList.forEach {
            if (it.dateTime().isEqual(lastJournal.dateTime())) {

                service.db.entryRepo.save(lastJournal.readJournal())
            } else {
                it.readJournal()
                service.db.journalRepo.save(it)
            }
        }
    }

    companion object : KLogging()
}