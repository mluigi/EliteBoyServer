package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class SellShipOnRebuy : JournalEntry() {
    var ShipType: String? = null
    var System: String? = null
    var SellShipId: Int? = null
    var ShipPrice: Long? = null
}