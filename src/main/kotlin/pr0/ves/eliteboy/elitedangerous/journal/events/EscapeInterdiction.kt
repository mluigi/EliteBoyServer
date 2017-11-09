package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class EscapeInterdiction : JournalEntry() {
    var Interdictor: String? = null
    var IsPlayer: Boolean? = null
}