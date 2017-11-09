package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry

import javax.persistence.Entity

@Entity
class RepairDrone : JournalEntry() {
    var HullRepaired: Double = 0.toDouble()
    var CockpitRepaired: Double = 0.toDouble()
    var CorrosionRepaired: Double = 0.toDouble()
}