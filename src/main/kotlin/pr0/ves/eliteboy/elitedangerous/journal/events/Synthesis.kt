package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry
import pr0.ves.eliteboy.elitedangerous.journal.events.util.BlueprintIngredients
import javax.persistence.Entity

@Entity
class Synthesis : JournalEntry() {
    var Name: String? = null
    var Materials: BlueprintIngredients? = null
}