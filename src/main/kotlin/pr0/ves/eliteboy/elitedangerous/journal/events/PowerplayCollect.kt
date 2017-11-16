package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class PowerplayCollect : JournalEntry() {
    var Power: String? = null
    var Type: String? = null
    var Count: Int? = null
}