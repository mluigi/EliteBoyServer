package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry
import javax.persistence.ElementCollection
import javax.persistence.Entity

@Entity
class PowerplayVoucher : JournalEntry() {
    var Power: String? = null
    @ElementCollection
    var Systems: MutableSet<String>? = null
}