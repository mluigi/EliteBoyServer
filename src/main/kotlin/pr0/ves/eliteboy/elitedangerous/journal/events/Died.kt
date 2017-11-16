package pr0.ves.eliteboy.elitedangerous.journal.events


import com.google.gson.annotations.SerializedName
import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry
import pr0.ves.eliteboy.elitedangerous.journal.events.util.Killer
import java.io.Serializable
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.OneToMany

@Entity
class Died : JournalEntry(), Serializable {
    @OneToMany(cascade = arrayOf(CascadeType.ALL), targetEntity = Killer::class)
    var Killers: MutableSet<Killer>? = null
    @SerializedName("KillerName", alternate = arrayOf("Name"))
    var KillerName: String? = null
    @SerializedName("KillerName_Localised", alternate = arrayOf("Name_Localised"))
    var KillerNameLocalised: String? = null
    @SerializedName("KillerShip", alternate = arrayOf("Ship"))
    var KillerShip: String? = null
    @SerializedName("KillerRank", alternate = arrayOf("Rank"))
    var KillerRank: String? = null
}