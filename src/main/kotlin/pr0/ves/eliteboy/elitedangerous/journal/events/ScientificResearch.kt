package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class ScientificResearch : JournalEntry() {
    var Name: String? = null
    var Count: Int = 0
    var Category: String? = null
}