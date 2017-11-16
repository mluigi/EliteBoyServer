package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class RestockVehicle : JournalEntry() {
    var Type: String? = null
    var Loadout: String? = null
    var Cost: Long? = null
    var Count: Int? = null
}