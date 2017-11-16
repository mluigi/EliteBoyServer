package pr0.ves.eliteboy.elitedangerous.journal.events.util

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var MissionID: Int? = null
    var Type: String? = null
    var VIP: Boolean? = null
    var Wanted: Boolean? = null
    var Count: Int? = null
}