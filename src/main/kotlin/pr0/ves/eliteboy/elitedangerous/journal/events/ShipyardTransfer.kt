package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class ShipyardTransfer : JournalEntry() {
    var ShipType: String? = null
    var ShipID: Int? = null
    var System: String? = null
    var Distance: Double? = null
    var TransferPrice: Long? = null
    var nTransferTime: Int? = null
}