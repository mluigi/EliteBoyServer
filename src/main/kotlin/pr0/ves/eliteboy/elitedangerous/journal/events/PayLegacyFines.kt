package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class PayLegacyFines : JournalEntry() {

    var Amount: Long? = null
    var BrokerPercentage: Double? = null
}