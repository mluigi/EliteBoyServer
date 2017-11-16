package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class RefuelAll : JournalEntry() {
    var Cost: Long = 0
    var Amount: Double = 0.toDouble()
}