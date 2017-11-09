package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class ShipyardTransfer : JournalEntry() {
    var ShipType: String? = null
    var ShipID: Int = 0
    var System: String? = null
    var Distance: Double = 0.toDouble()
    var TransferPrice: Long = 0
    var nTransferTime: Int = 0
}