package pr0.ves.eliteboy.elitedangerous.journal.events.util

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id


@Entity
class PowerState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var State: String? = null
    var Trend: Int? = null
}