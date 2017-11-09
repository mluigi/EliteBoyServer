package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry
import pr0.ves.eliteboy.elitedangerous.journal.events.util.CargoItem
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.OneToMany

@Entity
class Cargo : JournalEntry() {
    @OneToMany(cascade = arrayOf(CascadeType.ALL), targetEntity = CargoItem::class)
    var Inventory: MutableSet<CargoItem>? = null
}
