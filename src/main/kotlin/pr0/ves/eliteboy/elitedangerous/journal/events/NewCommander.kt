package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class NewCommander : JournalEntry() {
    var Name: String? = null
    var Package: String? = null
}