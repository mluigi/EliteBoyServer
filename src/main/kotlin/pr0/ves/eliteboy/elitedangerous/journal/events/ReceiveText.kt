package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry
import javax.persistence.Column

import javax.persistence.Entity

@Entity
class ReceiveText : JournalEntry() {
    @Column(name = "\"from\"")
    var From: String? = null
    var FromLocalised: String? = null
    var Message: String? = null
    var MessageLocalised: String? = null
    var Channel: String? = null
}