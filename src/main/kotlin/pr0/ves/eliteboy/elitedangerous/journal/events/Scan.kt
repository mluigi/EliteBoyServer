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
    var DistanceFromArrivalLS: Double = 0.toDouble()
    var nRotationPeriod: Double = 0.toDouble()
    var nSurfaceTemperature: Double = 0.toDouble()
    var Radius: Double = 0.toDouble()
    @OneToMany(cascade = arrayOf(CascadeType.ALL), targetEntity = StarPlanetRing::class)
    var Rings: MutableSet<StarPlanetRing>? = null

    var StarType: String? = null
    var StellarMass: Double = 0.toDouble()
    var AbsoluteMagnitude: Double = 0.toDouble()
    var Luminosity: String? = null
    @SerializedName("Age_MY")
    var Age: Double = 0.toDouble()

    var SemiMajorAxis: Double = 0.toDouble()
    var Eccentricity: Double = 0.toDouble()
    var OrbitalInclination: Double = 0.toDouble()
    var Periapsis: Double = 0.toDouble()
    var OrbitalPeriod: Double = 0.toDouble()
    var AxialTilt: Double = 0.toDouble()

    var PlanetClass: String? = null
    var TidalLock: Boolean? = null
    var TerraformState: String? = null
    var Atmosphere: String? = null
    @OneToMany(cascade = arrayOf(CascadeType.ALL), targetEntity = AtmoOrMat::class)
    var AtmosphereComposition: MutableSet<AtmoOrMat>? = null
    var Volcanism: String? = null
    var SurfaceGravity: Double = 0.toDouble()
    var SurfacePressure: Double = 0.toDouble()
    var Landable: Boolean? = null
    var MassEM: Double = 0.toDouble()
    @OneToMany(cascade = arrayOf(CascadeType.ALL), targetEntity = AtmoOrMat::class)
    var Materials: MutableSet<AtmoOrMat>? = null
    var ReserveLevel: String? = null
}