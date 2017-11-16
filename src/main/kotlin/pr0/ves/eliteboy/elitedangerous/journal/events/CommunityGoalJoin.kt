package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class CommunityGoalJoin : JournalEntry() {
    var Name: String? = null
    var System: String? = null
}