package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class CrewMemberRoleChange : JournalEntry() {
    var Crew: String? = null
    var Role: String? = null
}