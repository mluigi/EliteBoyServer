package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry
import pr0.ves.eliteboy.elitedangerous.journal.events.util.Material
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.OneToMany

@Entity
class Materials : JournalEntry() {
    @OneToMany(cascade = arrayOf(CascadeType.ALL), targetEntity = Material::class)
    var Raw: MutableSet<Material>? = null
    @OneToMany(cascade = arrayOf(CascadeType.ALL), targetEntity = Material::class)
    var Manufactured: MutableSet<Material>? = null
    @OneToMany(cascade = arrayOf(CascadeType.ALL), targetEntity = Material::class)
    var Encoded: MutableSet<Material>? = null

}


