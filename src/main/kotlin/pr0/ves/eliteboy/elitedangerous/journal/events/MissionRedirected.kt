package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class MissionRedirected : JournalEntry() {
    var NewDestinationStation: String? = null
    var OldDestinationStation: String? = null
    var NewDestinationSystem: String? = null
    var OldDestinationSystem: String? = null

    var MissionId: Int? = null
    var Name: String? = null
}