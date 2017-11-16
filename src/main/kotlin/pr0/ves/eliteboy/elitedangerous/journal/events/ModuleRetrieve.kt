package pr0.ves.eliteboy.elitedangerous.journal.events

import com.google.gson.annotations.SerializedName
import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class ModuleRetrieve : JournalEntry() {
    var Slot: String? = null
    var Ship: String? = null
    var ShipID: Int? = null
    var RetrievedItem: String? = null
    @SerializedName("RetrievedItem_Localised")
    var RetrievedItemLocalised: String? = null
    var EngineerModifications: String? = null
    var SwapOutItem: String? = null
    @SerializedName("SwapOutItem_Localised")
    var SwapOutItemLocalised: String? = null
    var Cost: Long? = null
}