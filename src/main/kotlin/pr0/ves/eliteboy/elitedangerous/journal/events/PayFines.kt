package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class PayFines : JournalEntry() {
    var Amount: Long = 0
    var BrokerPercentage: Double = 0.toDouble()
}