package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class PowerplayVote : JournalEntry() {
    var Power: String? = null
    var System: String? = null
    var Votes: Int = 0
}