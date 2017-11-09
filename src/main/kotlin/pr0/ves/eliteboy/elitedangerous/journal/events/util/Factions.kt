package pr0.ves.eliteboy.elitedangerous.journal.events.util

import javax.persistence.*

@Entity
class Factions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var Name: String? = null
    var FactionState: String? = null
    var Government: String? = null
    var Influence: Double = 0.toDouble()
    var Allegiance: String? = null
    @OneToMany(cascade = arrayOf(CascadeType.ALL), targetEntity = PowerState::class)
    var PendingStates: MutableSet<PowerState>? = null
    @OneToMany(cascade = arrayOf(CascadeType.ALL), targetEntity = PowerState::class)
    var RecoveringStates: MutableSet<PowerState>? = null
}