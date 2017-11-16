package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class MarketBuy : JournalEntry() {
    var Type: String? = null
    var Count: Int = 0
    var BuyPrice: Long = 0
    var TotalCost: Long = 0
}