package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class Progress : JournalEntry() {
    var Combat: Int? = null         // keep ints for backwards compat
    var Trade: Int? = null
    var Explore: Int? = null
    var Empire: Int? = null
    var Federation: Int? = null
    var CQC: Int? = null
}