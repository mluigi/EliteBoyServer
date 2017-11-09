package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class DatalinkVoucher : JournalEntry() {
    var PayeeFaction: String? = null
    var Reward: Long? = null
    var VictimFaction: String? = null
}