package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry
import pr0.ves.eliteboy.elitedangerous.journal.events.util.BlueprintIngredients
import javax.persistence.Entity

@Entity
class EngineerCraft : JournalEntry() {
    var Engineer: String? = null
    var Blueprint: String? = null
    var Level: Int? = null
    var Ingredients: BlueprintIngredients? = null


}

