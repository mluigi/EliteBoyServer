package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry
import javax.persistence.ElementCollection
import javax.persistence.Entity

@Entity
class Friends : JournalEntry() {
    var Status: String? = null      // used for single entries.. empty if list.  Used for VP backwards compat
    var Name: String? = null
    @ElementCollection
    var StatusMutableSet: MutableSet<String>? = null        // EDD addition.. used when agregating, null if single entry
    @ElementCollection
    var NameMutableSet: MutableSet<String>? = null

    var OnlineCount: Int = 0        // always counts
    var OfflineCount: Int = 0
}