package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class Liftoff : JournalEntry() {
    var Latitude: Double? = null
    var Longitude: Double? = null
    var PlayerControlled: Boolean? = null
}