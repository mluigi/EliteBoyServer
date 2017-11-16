package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry
import pr0.ves.eliteboy.elitedangerous.journal.events.util.Passenger
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.OneToMany

@Entity
class Passengers : JournalEntry() {
    @OneToMany(cascade = arrayOf(CascadeType.ALL), targetEntity = Passenger::class)
    var Manifest: MutableSet<Passenger>? = null
}

