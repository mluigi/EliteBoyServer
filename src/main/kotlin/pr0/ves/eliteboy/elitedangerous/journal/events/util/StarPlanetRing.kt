package pr0.ves.eliteboy.elitedangerous.journal.events.util

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id


@Entity
class StarPlanetRing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var Name: String? = null
    var RingClass: String? = null
    var MassMT: Double = 0.toDouble()
    var InnerRad: Double = 0.toDouble()
    var OuterRad: Double = 0.toDouble()
}