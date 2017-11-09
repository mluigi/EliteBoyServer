package pr0.ves.eliteboy.server.repositories

import org.springframework.data.repository.CrudRepository
import pr0.ves.eliteboy.elitedangerous.journal.Journal


interface JournalRepository : CrudRepository<Journal, Long> {
    fun existsByFilename(filename: String): Boolean
    fun findByFilename(filename: String): Journal
    fun findFirstByOrderByIdDesc(): Journal
}