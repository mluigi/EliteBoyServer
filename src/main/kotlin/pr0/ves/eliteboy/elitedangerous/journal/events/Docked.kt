package pr0.ves.eliteboy.elitedangerous.journal.events

import com.google.gson.annotations.SerializedName
import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry
import javax.persistence.ElementCollection
import javax.persistence.Entity

@Entity
class Docked : JournalEntry() {
    var StationName: String? = null
    var StationType: String? = null
    var StarSystem: String? = null
    var CockpitBreach: Boolean? = null
    @SerializedName("StationFaction", alternate = arrayOf("Faction"))
    var Faction: String? = null
    var FactionState: String? = null
    @SerializedName("StationAllegiance", alternate = arrayOf("Allegiance"))
    var Allegiance: String? = null
    @SerializedName("StationEconomy", alternate = arrayOf("Economy"))
    var Economy: String? = null
    @SerializedName("StationEconomy_Localised", alternate = arrayOf("Economy_Localised"))
    var EconomyLocalised: String? = null
    @SerializedName("StationGovernment", alternate = arrayOf("Government"))
    var Government: String? = null
    @SerializedName("StationGovernment_Localised", alternate = arrayOf("Government_Localised"))
    var GovernmentLocalised: String? = null
    @ElementCollection
    var StationServices: MutableSet<String>? = null
}