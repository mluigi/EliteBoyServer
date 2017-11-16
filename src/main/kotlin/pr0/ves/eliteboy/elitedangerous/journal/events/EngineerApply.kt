package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class EngineerApply : JournalEntry() {
    var Engineer: String? = null
    var Blueprint: String? = null
    var Level: Int? = null
    var Override: String? = null
}