package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry
import javax.persistence.ElementCollection
import javax.persistence.Entity

@Entity
class SellExplorationData : JournalEntry() {
    @ElementCollection
    var Systems: MutableSet<String>? = null
    @ElementCollection
    var Discovered: MutableSet<String>? = null
    var BaseValue: Long? = null
    var Bonus: Long? = null
}