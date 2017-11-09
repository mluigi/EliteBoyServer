package pr0.ves.eliteboy.elitedangerous.journal.events

import com.google.gson.annotations.SerializedName
import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class DatalinkScan : JournalEntry() {
    var Message: String? = null
    @SerializedName("Message_Localised")
    var MessageLocalised: String? = null
}