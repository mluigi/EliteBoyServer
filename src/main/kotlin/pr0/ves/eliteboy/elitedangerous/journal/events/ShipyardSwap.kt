package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class ShipyardSwap : JournalEntry() {
    var ShipType: String? = null
    var ShipFD: String? = null
    var ShipID: Int? = null
    var StoreOldShip: String? = null
    var StoreShipId: Int? = null
    var SellOldShip: String? = null
    var SellShipId: Int? = null
}