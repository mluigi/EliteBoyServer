package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class MaterialCollected : JournalEntry() {
    var Category: String? = null
    var Name: String? = null
    var Count: Int = 0
}