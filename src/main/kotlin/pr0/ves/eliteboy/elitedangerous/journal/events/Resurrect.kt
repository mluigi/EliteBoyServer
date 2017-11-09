package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class Resurrect : JournalEntry() {
    var Option: String? = null
    var Cost: Long = 0
    var Bankrupt: Boolean? = null
}