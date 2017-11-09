package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry
import javax.persistence.Column

import javax.persistence.Entity

@Entity
class VehicleSwitch : JournalEntry() {
    @Column(name = "\"to\"")
    var To: String? = null
}