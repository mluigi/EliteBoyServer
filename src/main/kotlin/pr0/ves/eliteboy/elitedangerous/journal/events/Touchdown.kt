package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class Touchdown : JournalEntry() {

    var Latitude: Double = 0.toDouble()
    var Longitude: Double = 0.toDouble()
    var PlayerControlled: Boolean? = null
}