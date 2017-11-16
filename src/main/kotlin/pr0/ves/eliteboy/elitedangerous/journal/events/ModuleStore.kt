package pr0.ves.eliteboy.elitedangerous.journal.events

import com.google.gson.annotations.SerializedName
import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class ModuleStore : JournalEntry() {
    var Slot: String? = null
    var Ship: String? = null
    var ShipID: Int? = null
    var StoredItem: String? = null
    @SerializedName("StoredItem_Localised")
    var StoredItemLocalised: String? = null
    var EngineerModifications: String? = null
    var ReplacementItem: String? = null
    @SerializedName("ReplacementItem_Localised")
    var ReplacementItemLocalised: String? = null
    var Cost: Long? = null
}