package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class Progress : JournalEntry() {
    var Combat: Int = 0         // keep ints for backwards compat
    var Trade: Int = 0
    var Explore: Int = 0
    var Empire: Int = 0
    var Federation: Int = 0
    var CQC: Int = 0
}