package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class DockingGranted : JournalEntry() {
    var StationName: String? = null
    var LandingPad: Int = 0
}