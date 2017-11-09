package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class JetConeDamage : JournalEntry() {
    var Module: String? = null
    var ModuleLocalised: String? = null
}