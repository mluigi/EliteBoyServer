package pr0.ves.eliteboy.elitedangerous.journal.events.util

import com.google.gson.annotations.SerializedName
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class ShipModule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var Slot: String? = null
    var Item: String? = null
    @SerializedName("Localised_Item")
    var LocalisedItem: String? = null
    @SerializedName("On")
    var Enabled: Boolean? = null
    var Priority: Int = 0
    var AmmoInClip: Int = 0
    var AmmoInHopper: Int = 0
    var Blueprint: String? = null
    var BlueprintLevel: Int = 0
    var Health: Int = 0
    var Value: Long = 0
}