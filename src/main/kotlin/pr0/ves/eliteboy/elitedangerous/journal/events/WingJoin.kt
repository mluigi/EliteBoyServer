package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry
import javax.persistence.ElementCollection
import javax.persistence.Entity

@Entity
class WingJoin : JournalEntry() {
    @ElementCollection
    var Others: MutableSet<String>? = null
}