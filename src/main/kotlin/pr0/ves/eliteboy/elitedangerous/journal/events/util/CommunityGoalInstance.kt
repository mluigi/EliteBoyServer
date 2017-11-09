package pr0.ves.eliteboy.elitedangerous.journal.events.util

import java.time.OffsetDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class CommunityGoalInstance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var CGID: Int = 0
    var Title: String? = null
    var SystemName: String? = null
    var MarketName: String? = null
    var Expiry: String? = null
    fun expiry(): OffsetDateTime = OffsetDateTime.parse(Expiry)
    var IsComplete: Boolean? = null
    var CurrentTotal: Long = 0
    var PlayerContribution: Long = 0
    var NumContributors: Int = 0
    var PlayerPercentileBand: Int = 0
    var TopRankSize: Int = 0
    var PlayerInTopRank: Boolean? = null
    var TierReached: String? = null
    var Bonus: Long = 0
}