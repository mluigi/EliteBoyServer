package pr0.ves.eliteboy.elitedangerous.journal.events

import com.google.gson.annotations.SerializedName
import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class FetchRemoteModule : JournalEntry() {
    var StorageSlot: String? = null
    var StoredItem: String? = null
    var StoredItemFD: String? = null
    @SerializedName("StoredItem_Localised")
    var StoredItemLocalised: String? = null
    var TransferCost: Long? = null
    var Ship: String? = null
    var ShipID: Int? = null
    var ServerId: Int? = null
    var nTransferTime: Int? = null
}