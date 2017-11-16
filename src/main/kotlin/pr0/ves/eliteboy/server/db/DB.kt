package pr0.ves.eliteboy.server.db

import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.sqlite.SQLiteException
import pr0.ves.eliteboy.elitedangerous.journal.Journal
import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry
import pr0.ves.eliteboy.server.Settings
import pr0.ves.eliteboy.server.listeners.JournalWatcherListener
import pr0.ves.eliteboy.server.repositories.JournalEntryRepository
import pr0.ves.eliteboy.server.repositories.JournalRepository

@Component
class DB @Autowired constructor(var journalRepo: JournalRepository,
                                var entryRepo: JournalEntryRepository) : JournalWatcherListener {

    @Autowired
    lateinit var settings: Settings


    override fun getEntries(entries: ArrayList<JournalEntry>) {
        entryRepo.save(entries)
    }

    @Transactional
    fun populateDB() {
        logger.info("First initialization. It's gonna take some time.")
        val folder = Journal.FDDIR
        var i = 1
        var size = 0
        folder.listFiles()
                .filter { it.isFile && it.name.startsWith("Journal", true) }
                .also { size = it.size }
                .forEach {
                    val journal = Journal(it.name)
                    journal.readJournal()
                    try {
                        if (!journalRepo.existsByFilename(journal.filename))
                            journalRepo.save(journal)
                    } catch (e: SQLiteException) {
                        logger.error("", e)
                    }

                    logger.info("$i/$size journals added to db.")
                    i++
                }
        settings.dbNeedsInitialization = false
        logger.info("DB inizialization done")

    }

    companion object : KLogging()
}