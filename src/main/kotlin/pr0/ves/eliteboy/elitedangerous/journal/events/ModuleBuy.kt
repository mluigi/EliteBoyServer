package pr0.ves.eliteboy.elitedangerous.journal.events

import com.google.gson.annotations.SerializedName
import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class ModuleBuy : JournalEntry() {
    var Slot: String? = null
    var Ship: String? = null
    var ShipID: Int = 0
    var BuyItem: String? = null
    @SerializedName("BuyItem_Localised")
    var BuyItemLocalised: String? = null
    var BuyPrice: Long = 0
    var SellItem: String? = null
    var SellItemFD: String? = null
    @SerializedName("SellItem_Localised")
    var SellItemLocalised: String? = null
    var SellPrice: Long = 0
    var StoredItem: String? = null
    var StoredItemLocalised: String? = null
}