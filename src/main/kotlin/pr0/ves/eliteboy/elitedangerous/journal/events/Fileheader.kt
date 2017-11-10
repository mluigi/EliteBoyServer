package pr0.ves.eliteboy.elitedangerous.journal.events

import com.google.gson.annotations.SerializedName
import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class Fileheader : JournalEntry() {
    @SerializedName("gameversion")
    var gameVersion: String? = null
    var build: String? = null
    var language: String? = null
    var part: Int? = null
}