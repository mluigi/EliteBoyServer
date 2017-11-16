package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class Undocked : JournalEntry() {
    var StationName: String? = null
    var StationType: String? = null
}