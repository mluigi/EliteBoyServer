package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class FuelScoop : JournalEntry() {
    var Scooped: Double? = null
    var Total: Double? = null
}