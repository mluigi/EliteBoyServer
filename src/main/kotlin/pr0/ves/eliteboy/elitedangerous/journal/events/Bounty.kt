package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry
import pr0.ves.eliteboy.elitedangerous.journal.events.util.BountyReward
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.OneToMany

@Entity
class Bounty : JournalEntry() {
    var TotalReward: Long? = null
    var VictimFaction: String? = null
    var VictimFactionLocalised: String? = null
    var Target: String? = null
    var SharedWithOthers: Boolean? = null
    @OneToMany(cascade = arrayOf(CascadeType.ALL), targetEntity = BountyReward::class)
    var Rewards: MutableSet<BountyReward>? = null
}