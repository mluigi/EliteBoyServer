package pr0.ves.eliteboy.elitedangerous.journal.events

import com.google.gson.annotations.SerializedName
import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class USSDrop : JournalEntry() {
    var USSType: String? = null
    var USSThreat: Int = 0
    @SerializedName("USSType_Localised")
    var USSTypeLocalised: String? = null
}