package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class Screenshot : JournalEntry() {

    var Filename: String? = null
    var Width: Int = 0
    var Height: Int = 0
    var System: String? = null
    var Body: String? = null
    var nLatitude: Double = 0.toDouble()
    var nLongitude: Double = 0.toDouble()
}