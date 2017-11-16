package pr0.ves.eliteboy.server.repositories

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import pr0.ves.eliteboy.elitedangerous.journal.Journal
import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

interface JournalEntryRepository : PagingAndSortingRepository<JournalEntry, Long> {
    fun findByEvent(event: String): ArrayList<JournalEntry>
    fun countByEvent(event: String): Int
    fun existsByNLineAndTimestampAndEvent(nLine: Int, timestamp: String, event: String): Boolean
    fun findDinstinctByEvent(event: String): ArrayList<JournalEntry>
    fun findByJournal(journal: Journal): ArrayList<JournalEntry>

    @Query("SELECT j FROM JournalEntry j WHERE DATETIME(j.timestamp) >= DATETIME(?1)")
    fun findEventAfterDate(date: String)
}