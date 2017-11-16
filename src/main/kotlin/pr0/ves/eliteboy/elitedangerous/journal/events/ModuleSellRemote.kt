package pr0.ves.eliteboy.elitedangerous.journal.events

import com.google.gson.annotations.SerializedName
import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class ModuleSellRemote : JournalEntry() {

    var Slot: String? = null
    var SellItem: String? = null
    @SerializedName("SellItem_Localised")
    var SellItemLocalised: String? = null
    var SellPrice: Long? = null
    var Ship: String? = null
    var ShipID: Int? = null
    var ServerId: Int? = null
}