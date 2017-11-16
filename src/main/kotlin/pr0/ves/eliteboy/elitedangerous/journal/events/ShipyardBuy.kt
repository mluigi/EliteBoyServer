package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class ShipyardBuy : JournalEntry() {
    var ShipType: String? = null
    var ShipPrice: Long = 0
    var StoreOldShip: String? = null
    var StoreShipId: Int = 0
    var SellOldShip: String? = null
    var SellShipId: Int = 0
    var SellPrice: Long = 0
}