package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class Screenshot : JournalEntry() {

    var Filename: String? = null
    var Width: Int? = null
    var Height: Int? = null
    var System: String? = null
    var Body: String? = null
    var nLatitude: Double? = null
    var nLongitude: Double? = null
}