package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class Repair : JournalEntry() {
    var Item: String? = null
    var ItemLocalised: String? = null
    var Cost: Long = 0
}