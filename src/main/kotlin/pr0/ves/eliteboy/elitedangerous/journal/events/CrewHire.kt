package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.companionapi.CombatRank.values
import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry
import javax.persistence.Entity

@Entity
class CrewHire : JournalEntry() {
    var Name: String? = null
    var Faction: String? = null
    var Cost: Long? = null
    var CombatRank: Int? = null
    fun combatRank() = values()[CombatRank!!]
}