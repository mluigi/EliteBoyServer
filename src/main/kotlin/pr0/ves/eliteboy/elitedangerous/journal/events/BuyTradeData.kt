package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class BuyTradeData : JournalEntry() {
    var System: String? = null
    var Cost: Long = 0
}