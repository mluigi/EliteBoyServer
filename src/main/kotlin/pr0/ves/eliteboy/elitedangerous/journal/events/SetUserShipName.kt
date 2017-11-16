package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class SetUserShipName : JournalEntry() {
    var Ship: String? = null
    var ShipID: Int? = null
    var ShipName: String? = null
    var ShipIdent: String? = null
}