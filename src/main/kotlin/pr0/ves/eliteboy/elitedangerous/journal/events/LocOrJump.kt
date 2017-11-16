package pr0.ves.eliteboy.elitedangerous.journal.events


interface LocOrJump {
    var StarSystem: String?
    var StarPos: MutableSet<Double>?
    var Allegiance: String?
    var SystemEconomy: String?
    var SystemEconomyLocalised: String?
    var SystemGovernment: String?
    var SystemGovernmentLocalised: String?
    var SystemSecurity: String?
    var SystemSecurityLocalised: String?
    var Population: Long?
    var SystemFaction: String?
}


