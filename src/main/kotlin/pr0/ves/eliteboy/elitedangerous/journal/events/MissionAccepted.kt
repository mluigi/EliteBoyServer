package pr0.ves.eliteboy.elitedangerous.journal.events

import com.google.gson.annotations.SerializedName
import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry
import java.time.OffsetDateTime

import javax.persistence.Entity

@Entity
class MissionAccepted : JournalEntry() {
    var Faction: String? = null
    var Name: String? = null

    var TargetType: String? = null
    @SerializedName("TargetType_Localised")
    var TargetTypeLocalised: String? = null
    var TargetFaction: String? = null
    var DestinationSystem: String? = null
    var DestinationStation: String? = null
    var Target: String? = null
    var TargetFriendly: String? = null
    @SerializedName("Target_Localised")
    var TargetLocalised: String? = null
    var Expiry: String? = null
    fun expiry(): OffsetDateTime = OffsetDateTime.parse(Expiry)
    var Influence: String? = null
    var Reputation: String? = null
    var MissionId: Int = 0

    var Commodity: String? = null
    @SerializedName("Commodity_Localised")
    var CommodityLocalised: String? = null
    var Count: Int = 0

    var PassengerCount: Int = 0
    var PassengerVIPs: Boolean? = null
    var PassengerWanted: Boolean? = null
    var PassengerType: String? = null
}