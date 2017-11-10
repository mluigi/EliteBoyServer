package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry
import pr0.ves.eliteboy.elitedangerous.journal.events.util.CommodityRew
import javax.persistence.CascadeType
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.OneToMany

@Entity
class MissionCompleted : JournalEntry() {
    var Name: String? = null
    var Faction: String? = null

    var Commodity: String? = null               // FDNAME, leave, evidence of the $_name problem
    var CommodityLocalised: String? = null
    var FriendlyCommodity: String? = null
    var Count: Int? = null

    var Target: String? = null
    var TargetLocalised: String? = null
    var TargetFriendly: String? = null
    var TargetType: String? = null
    var TargetTypeLocalised: String? = null
    var TargetTypeFriendly: String? = null
    var TargetFaction: String? = null

    var DestinationSystem: String? = null
    var DestinationStation: String? = null

    var Reward: Long? = null
    var Donation: Long? = null
    @ElementCollection
    var PermitsAwarded: MutableSet<String>? = null
    var MissionId: Int? = null
    @OneToMany(cascade = arrayOf(CascadeType.ALL), targetEntity = CommodityRew::class)
    var CommodityReward: MutableSet<CommodityRew>? = null
}

