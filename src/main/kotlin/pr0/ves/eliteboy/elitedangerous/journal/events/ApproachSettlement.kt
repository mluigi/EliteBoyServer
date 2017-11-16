package pr0.ves.eliteboy.elitedangerous.journal.events

import com.google.gson.annotations.SerializedName
import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class ApproachSettlement : JournalEntry() {
    var Name: String? = null
    @SerializedName("Name_Localised")
    var NameLocalised: String? = null
}