package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class MiningRefined : JournalEntry() {
    var Type: String? = null
    var FriendlyType: String? = null
}