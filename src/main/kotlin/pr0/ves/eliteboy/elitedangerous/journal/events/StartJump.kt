package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class StartJump : JournalEntry() {
    var JumpType: String? = null
    var StarSystem: String? = null
    var StarClass: String? = null
}