package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class SellDrones : JournalEntry() {
    var Type: String? = null
    var Count: Int? = null
    var SellPrice: Long? = null
    var TotalSale: Long? = null
}