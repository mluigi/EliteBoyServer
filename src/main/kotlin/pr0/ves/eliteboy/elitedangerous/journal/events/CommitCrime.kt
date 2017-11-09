package pr0.ves.eliteboy.elitedangerous.journal.events

import com.google.gson.annotations.SerializedName
import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class CommitCrime : JournalEntry() {
    var CrimeType: String? = null
    var Faction: String? = null
    var Victim: String? = null
    @SerializedName("Victim_Localised")
    var VictimLocalised: String? = null
    var Fine: Long? = null
    var Bounty: Long? = null
}