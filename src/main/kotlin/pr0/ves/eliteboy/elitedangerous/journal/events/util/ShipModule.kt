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
    var Priority: Int? = null
    var AmmoInClip: Int? = null
    var AmmoInHopper: Int? = null
    var Blueprint: String? = null
    var BlueprintLevel: Int? = null
    var Health: Int? = null
    var Value: Long? = null
}