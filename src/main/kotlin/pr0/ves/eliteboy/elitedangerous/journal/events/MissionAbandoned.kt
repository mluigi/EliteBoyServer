package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class MissionAbandoned : JournalEntry() {
    var Name: String? = null
    var MissionId: Int? = null
}