package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.companionapi.*
import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class Rank : JournalEntry() {
    var Combat: Int? = null
    fun combat() = CombatRank.values()[Combat!!]
    var Trade: Int? = null
    fun trade() = TradeRank.values()[Trade!!]
    var Explore: Int? = null
    fun exploration() = ExplorationRank.values()[Explore!!]
    var CQC: Int? = null
    fun cqc() = CQCRank.values()[CQC!!]
    var Federation: Int? = null
    fun federation() = FederationRank.values()[Federation!!]
    var Empire: Int? = null
    fun empire() = EmpireRank.values()[Empire!!]
}