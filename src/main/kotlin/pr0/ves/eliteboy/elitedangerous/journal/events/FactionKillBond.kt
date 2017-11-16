package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class FactionKillBond : JournalEntry() {
    var AwardingFaction: String? = null
    var VictimFaction: String? = null
    var Reward: Long = 0
}