package pr0.ves.eliteboy.elitedangerous.journal.events

import com.google.gson.annotations.SerializedName
import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class AfmuRepairs : JournalEntry() {
    var Module: String? = null
    @SerializedName("Module_Localised")
    var ModuleLocalised: String? = null
    var FullyRepaired: Boolean? = null
    var Health: Float? = null
}