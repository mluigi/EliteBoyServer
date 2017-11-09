package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry


import javax.persistence.Entity

@Entity
class PVPKill : JournalEntry() {
    var Victim: String? = null
    var CombatRank: Int? = null
    fun combatRank() = pr0.ves.eliteboy.elitedangerous.companionapi.CombatRank.values()[CombatRank!!]
}