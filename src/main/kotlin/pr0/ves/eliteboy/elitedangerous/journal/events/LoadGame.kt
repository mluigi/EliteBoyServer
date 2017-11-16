package pr0.ves.eliteboy.elitedangerous.journal.events

import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry
import javax.persistence.Column

import javax.persistence.Entity

@Entity
class LoadGame : JournalEntry() {
    var Commander: String? = null
    var Ship: String? = null
    var ShipID: Int? = null
    var StartLanded: Boolean? = null
    var StartDead: Boolean? = null
    var GameMode: String? = null
    @Column(name = "\"group\"")
    var Group: String? = null
    var Credits: Long? = null
    var Loan: Long? = null

    var ShipName: String? = null
    var ShipIdent: String? = null
    var FuelLevel: Double? = null
    var FuelCapacity: Double? = null
}