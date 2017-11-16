package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class PowerplayFastTrack : JournalEntry() {
    var Power: String? = null
    var Cost: Long = 0
}