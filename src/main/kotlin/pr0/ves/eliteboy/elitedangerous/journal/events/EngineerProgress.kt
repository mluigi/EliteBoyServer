package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class EngineerProgress : JournalEntry() {
    var Engineer: String? = null
    var Progress: String? = null
    var Rank: Int? = null
}