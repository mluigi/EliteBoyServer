package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class RedeemVoucher : JournalEntry() {
    var Type: String? = null
    var Amount: Long = 0
    var Faction: String? = null
    var BrokerPercentage: Double = 0.toDouble()
}