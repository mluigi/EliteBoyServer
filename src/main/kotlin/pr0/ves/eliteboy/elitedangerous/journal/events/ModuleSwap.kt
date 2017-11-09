package pr0.ves.eliteboy.elitedangerous.journal.events

import com.google.gson.annotations.SerializedName
import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class ModuleSwap : JournalEntry() {
    var FromSlot: String? = null
    var ToSlot: String? = null
    var FromItem: String? = null
    @SerializedName("FromItem_Localised")
    var FromItemLocalised: String? = null
    var ToItem: String? = null
    @SerializedName("ToItem_Localised")
    var ToItemLocalised: String? = null
    var Ship: String? = null
    var ShipID: Int = 0
}