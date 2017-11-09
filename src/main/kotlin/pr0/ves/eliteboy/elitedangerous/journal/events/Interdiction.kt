package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.companionapi.CombatRank.values
import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class Interdiction : JournalEntry() {
    var Success: Boolean? = null
    var Interdicted: String? = null
    var IsPlayer: Boolean? = null
    var CombatRank: Int? = null
    fun combatRank() = values()[CombatRank!!]
    var Faction: String? = null
    var Power: String? = null
}