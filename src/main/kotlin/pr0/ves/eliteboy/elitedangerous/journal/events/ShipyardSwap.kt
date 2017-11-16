package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class ShipyardSwap : JournalEntry() {
    var ShipType: String? = null
    var ShipFD: String? = null
    var ShipID: Int = 0
    var StoreOldShip: String? = null
    var StoreShipId: Int = 0
}