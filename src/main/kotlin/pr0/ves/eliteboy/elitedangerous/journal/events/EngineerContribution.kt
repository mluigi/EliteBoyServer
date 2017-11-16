package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class EngineerContribution : JournalEntry() {
    private var unknownType: Boolean? = null
    var Engineer: String? = null
    var Type: String? = null
    var Commodity: String? = null
    var Material: String? = null
    var Quantity: Int = 0
    var TotalQuantity: Int = 0
}