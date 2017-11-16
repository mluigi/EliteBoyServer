package pr0.ves.eliteboy.elitedangerous.journal.events

import com.google.gson.annotations.SerializedName
import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry
import pr0.ves.eliteboy.elitedangerous.journal.events.util.AtmoOrMat
import pr0.ves.eliteboy.elitedangerous.journal.events.util.StarPlanetRing
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.OneToMany

@Entity
class Scan : JournalEntry() {
    var BodyName: String? = null
    var DistanceFromArrivalLS: Double? = null
    var nRotationPeriod: Double? = null
    var nSurfaceTemperature: Double? = null
    var Radius: Double? = null
    @OneToMany(cascade = arrayOf(CascadeType.ALL), targetEntity = StarPlanetRing::class)
    var Rings: MutableSet<StarPlanetRing>? = null

    var StarType: String? = null
    var StellarMass: Double? = null
    var AbsoluteMagnitude: Double? = null
    var Luminosity: String? = null
    @SerializedName("Age_MY")
    var Age: Double? = null

    var SemiMajorAxis: Double? = null
    var Eccentricity: Double? = null
    var OrbitalInclination: Double? = null
    var Periapsis: Double? = null
    var OrbitalPeriod: Double? = null
    var AxialTilt: Double? = null

    var PlanetClass: String? = null
    var TidalLock: Boolean? = null
    var TerraformState: String? = null
    var Atmosphere: String? = null
    @OneToMany(cascade = arrayOf(CascadeType.ALL), targetEntity = AtmoOrMat::class)
    var AtmosphereComposition: MutableSet<AtmoOrMat>? = null
    var Volcanism: String? = null
    var SurfaceGravity: Double? = null
    var SurfacePressure: Double? = null
    var Landable: Boolean? = null
    var MassEM: Double? = null
    @OneToMany(cascade = arrayOf(CascadeType.ALL), targetEntity = AtmoOrMat::class)
    var Materials: MutableSet<AtmoOrMat>? = null
    var ReserveLevel: String? = null
}