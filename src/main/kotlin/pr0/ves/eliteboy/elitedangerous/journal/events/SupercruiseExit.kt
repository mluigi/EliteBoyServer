package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class SupercruiseExit : JournalEntry() {
    var StarSystem: String? = null
    var Body: String? = null
    var BodyType: String? = null
}