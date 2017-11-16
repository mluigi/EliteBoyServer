package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry
import pr0.ves.eliteboy.elitedangerous.journal.events.util.ShipModule
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.OneToMany

@Entity
class Loadout : JournalEntry() {
    var Ship: String? = null
    var ShipID: Int? = null
    var ShipName: String? = null
    var ShipIdent: String? = null
    @OneToMany(cascade = arrayOf(CascadeType.ALL), targetEntity = ShipModule::class)
    var Modules: MutableSet<ShipModule>? = null


}

