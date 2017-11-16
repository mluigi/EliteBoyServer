package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class PowerplaySalary : JournalEntry() {
    var Power: String? = null
    var Amount: Long? = null
}