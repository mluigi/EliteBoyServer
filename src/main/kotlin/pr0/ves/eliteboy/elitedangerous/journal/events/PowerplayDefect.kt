package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class PowerplayDefect : JournalEntry() {
    var FromPower: String? = null
    var ToPower: String? = null
}