package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry
import javax.persistence.Column

import javax.persistence.Entity

@Entity
class LoadGame : JournalEntry() {
    var Commander: String? = null
    var Ship: String? = null
    var ShipID: Int = 0
    var StartLanded: Boolean? = null
    var StartDead: Boolean? = null
    var GameMode: String? = null
    @Column(name = "\"group\"")
    var Group: String? = null
    var Credits: Long = 0
    var Loan: Long = 0

    var ShipName: String? = null
    var ShipIdent: String? = null
    var FuelLevel: Double = 0.toDouble()
    var FuelCapacity: Double = 0.toDouble()
}