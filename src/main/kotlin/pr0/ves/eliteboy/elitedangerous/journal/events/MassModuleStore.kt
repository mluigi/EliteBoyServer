package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry
import javax.persistence.*

@Entity
class MassModuleStore : JournalEntry() {
    var Ship: String? = null
    var ShipFD: String? = null
    var ShipID: Int = 0
    @OneToMany(cascade = arrayOf(CascadeType.ALL), targetEntity = ModuleItem::class)
    var ModuleItems: MutableSet<ModuleItem>? = null

    @Entity
    class ModuleItem {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null
        var Slot: String? = null
        var Name: String? = null
        var EngineerModifications: String? = null
    }
}