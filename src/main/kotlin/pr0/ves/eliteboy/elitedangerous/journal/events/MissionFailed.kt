package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class MissionFailed : JournalEntry() {
    var Name: String? = null
    var MissionId: Int = 0
}