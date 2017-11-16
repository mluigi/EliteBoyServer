package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry
import pr0.ves.eliteboy.elitedangerous.journal.events.util.CommunityGoalInstance
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.OneToMany

@Entity
class CommunityGoal : JournalEntry() {
    @OneToMany(cascade = arrayOf(CascadeType.ALL), targetEntity = CommunityGoalInstance::class)
    var CommunityGoals: MutableSet<CommunityGoalInstance>? = null
    var CommunityGoalMutableSet: String? = null

}

