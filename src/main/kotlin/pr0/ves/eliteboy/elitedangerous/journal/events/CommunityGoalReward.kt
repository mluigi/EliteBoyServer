package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class CommunityGoalReward : JournalEntry() {
    var Name: String? = null
    var System: String? = null
    var Reward: Long? = null
}