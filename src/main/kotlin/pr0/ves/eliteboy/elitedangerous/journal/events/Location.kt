package pr0.ves.eliteboy.elitedangerous.journal.events

import com.google.gson.annotations.SerializedName
import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry
import pr0.ves.eliteboy.elitedangerous.journal.events.util.Factions
import javax.persistence.CascadeType
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.OneToMany

@Entity
class Location : JournalEntry(), LocOrJump {
    var Docked: Boolean? = null
    var StationName: String? = null
    var StationType: String? = null
    var Body: String? = null
    var BodyType: String? = null
    var Latitude: Double? = null
    var Longitude: Double? = null

    override var StarSystem: String? = null
    @ElementCollection
    override var StarPos: MutableSet<Double>? = null
    @SerializedName("SystemAllegiance", alternate = arrayOf("Allegiance"))
    override var Allegiance: String? = null
    @SerializedName("SystemEconomy", alternate = arrayOf("Economy"))
    override var SystemEconomy: String? = null
    @SerializedName("SystemEconomy_Localised", alternate = arrayOf("Economy_Localised"))
    override var SystemEconomyLocalised: String? = null
    @SerializedName("SystemGovernment", alternate = arrayOf("Government"))
    override var SystemGovernment: String? = null
    @SerializedName("SystemGovernment_Localised", alternate = arrayOf("Government_Localised"))
    override var SystemGovernmentLocalised: String? = null
    @SerializedName("SystemSecurity", alternate = arrayOf("Security"))
    override var SystemSecurity: String? = null
    @SerializedName("SystemSecurity_Localised", alternate = arrayOf("Security_Localised"))
    override var SystemSecurityLocalised: String? = null
    override var Population: Long? = null
    @SerializedName("SystemFaction", alternate = arrayOf("Faction"))
    override var SystemFaction: String? = null

    @OneToMany(cascade = arrayOf(CascadeType.ALL), targetEntity = pr0.ves.eliteboy.elitedangerous.journal.events.util.Factions::class)
    @SerializedName("Factions")
    var Factions: MutableSet<Factions>? = null
}