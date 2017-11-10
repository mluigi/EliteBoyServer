package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class RepairDrone : JournalEntry() {
    var HullRepaired: Double? = null
    var CockpitRepaired: Double? = null
    var CorrosionRepaired: Double? = null
}