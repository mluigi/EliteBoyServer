package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class BuyDrones : JournalEntry() {
    var Type: String? = null
    var Count: Int? = null
    var BuyPrice: Long? = null
    var TotalCost: Long? = null

}