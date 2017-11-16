package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class BuyExplorationData : JournalEntry() {
    var System: String? = null
    var Cost: Long? = null
}