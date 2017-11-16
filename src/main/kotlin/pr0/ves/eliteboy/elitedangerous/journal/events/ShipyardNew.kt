package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class ShipyardNew : JournalEntry() {
    var ShipType: String? = null
    var NewShipId: Int? = null
}