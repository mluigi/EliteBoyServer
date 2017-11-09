package pr0.ves.eliteboy.elitedangerous.edsm.data

class Station {
    var id: Int = 0
    var id64: Long? = null
    var name: String? = null
    var type: String? = null
    var distanceToArrival: Double = 0.toDouble()
    var allegiance: String? = null
    var government: String? = null
    var economy: String? = null
    var haveMarket: Boolean = false
    var haveShipyard: Boolean = false
    var controllingFaction: ControllingFaction? = null

    class ControllingFaction {
        var id: Int = 0
        var name: String? = null
    }
}