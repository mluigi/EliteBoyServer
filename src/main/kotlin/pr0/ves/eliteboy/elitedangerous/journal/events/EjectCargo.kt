package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class EjectCargo : JournalEntry() {
    var Type: String? = null
    var Count: Int? = null
    var Abandoned: Boolean? = null
    var PowerplayOrigin: String? = null
}