package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry
import javax.persistence.ElementCollection
import javax.persistence.Entity

@Entity
class RebootRepair : JournalEntry() {
    @ElementCollection
    var Modules: MutableSet<String>? = null
    @ElementCollection
    var FriendlyModules: MutableSet<String>? = null

}