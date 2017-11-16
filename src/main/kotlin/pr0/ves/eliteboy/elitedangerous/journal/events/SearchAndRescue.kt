package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class SearchAndRescue : JournalEntry() {
    var Name: String? = null
    var FriendlyName: String? = null
    var Count: Int? = null
    var Reward: Long? = null
}