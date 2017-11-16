package pr0.ves.eliteboy.elitedangerous.journal.events.util

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Killer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var Name: String? = null
    var Ship: String? = null
    var Rank: String? = null
}