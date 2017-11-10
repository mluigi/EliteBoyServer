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
    var CGID: Int? = null
    var Title: String? = null
    var SystemName: String? = null
    var MarketName: String? = null
    var Expiry: String? = null
    fun expiry(): OffsetDateTime = OffsetDateTime.parse(Expiry)
    var IsComplete: Boolean? = null
    var CurrentTotal: Long? = null
    var PlayerContribution: Long? = null
    var NumContributors: Int? = null
    var PlayerPercentileBand: Int? = null
    var TopRankSize: Int? = null
    var PlayerInTopRank: Boolean? = null
    var TierReached: String? = null
    var Bonus: Long? = null
}