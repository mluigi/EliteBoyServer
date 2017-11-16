package pr0.ves.eliteboy.elitedangerous.edsm

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import mu.KLogging
import pr0.ves.eliteboy.elitedangerous.edsm.data.Log
import pr0.ves.eliteboy.elitedangerous.edsm.data.System
import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry
import pr0.ves.eliteboy.elitedangerous.journal.events.*
import pr0.ves.eliteboy.server.listeners.JournalWatcherListener
import java.io.DataOutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.time.format.DateTimeFormatter

class EDSMApi(var commander: String = "", var apiKey: String = "") : JournalWatcherListener {
    companion object : KLogging() {
        val BASE_URL = "https://www.edsm.net"
        val STATUS = "/api-status-v1/elite-server"

        val SYSTEM = "/api-v1/system"
        val SYSTEMS = "/api-v1/systems"
        val SPHERE_SYSTEMS = "/api-v1/sphere-systems"
        val CUBE_SYSTEMS = "/api-v1/cube-systems"

        val BODIES = "/api-system-v1/bodies"
        val STATIONS = "/api-system-v1/stations"
        val FACTIONS = "/api-system-v1/factions"

        val SET_LOG = "/api-logs-v1/set-log"
        val GET_LOGS = "/api-logs-v1/get-logs"
        val GET_RANKS = "/api-commander-v1/get-ranks"
        val SET_RANKS = "/api-commander-v1/set-ranks"
        val SET_CREDITS = "/api-commander-v1/set-credits"
        val SET_CURRENT_SHIP = "/api-commander-v1/set-ship-id"
        val UPDATE_SHIP = "/api-commander-v1/update-ship"
        val SELL_SHIP = "/api-commander-v1/sell-ship"
        val SET_MATS = "/api-commander-v1/set-materials"

        val FROM_SOFTWARE = "EliteBoy"
        val FROM_SOFTWARE_VERSION = "1.0"

        enum class SearchType {
            NAME,
            SPHERE,
            CUBE
        }
    }


    fun getSystem(name: String): System {
        val system = URLEncoder.encode(name, "UTF-8")
        val connection = URL(BASE_URL + SYSTEM +
                "/?systemName=$system" +
                "&showId=1" +
                "&showCoordinates=1" +
                "&showPermit=1" +
                "&showInformation=1" +
                "&showPrimaryStar=1").openConnection()
        connection.doInput = true
        val json = connection.getInputStream().bufferedReader().readText()
        return Gson().fromJson<System>(json, System::class.java)
    }

    private fun findSystems(type: SearchType, system: String = "", x: Double = 0.0, y: Double = 0.0, z: Double = 0.0, size: Int = 0): ArrayList<System> {
        var url = BASE_URL
        val systemEncoded = URLEncoder.encode(system, "UTF-8")
        when (type) {
            SearchType.NAME -> {
                url += SYSTEMS +
                        "/?systemName=$systemEncoded" +
                        "&showId=1" +
                        "&showCoordinates=1" +
                        "&showPermit=1" +
                        "&showInformation=1" +
                        "&showPrimaryStar=1"
            }
            SearchType.SPHERE -> {
                if (system == "") {
                    url += SPHERE_SYSTEMS +
                            "/?x=$x&y=$y&z=$z" +
                            "&showId=1" +
                            "&showCoordinates=1" +
                            "&showPermit=1" +
                            "&showInformation=1" +
                            "&showPrimaryStar=1" +
                            if (size > 0) {
                                "&radius=$size"
                            } else {
                            }
                } else {
                    url += SPHERE_SYSTEMS +
                            "/?systemName=$systemEncoded" +
                            "&showId=1" +
                            "&showCoordinates=1" +
                            "&showPermit=1" +
                            "&showInformation=1" +
                            "&showPrimaryStar=1" +
                            if (size > 0) {
                                "&radius=$size"
                            } else {
                            }
                }
            }
            SearchType.CUBE -> {
                if (system == "") {
                    url += CUBE_SYSTEMS +
                            "/?x=$x&y=$y&z=$z" +
                            "&showId=1" +
                            "&showCoordinates=1" +
                            "&showPermit=1" +
                            "&showInformation=1" +
                            "&showPrimaryStar=1" +
                            if (size > 0) {
                                "&size=$size"
                            } else {
                            }
                } else {
                    url += CUBE_SYSTEMS +
                            "/?systemName=$systemEncoded" +
                            "&showId=1" +
                            "&showCoordinates=1" +
                            "&showPermit=1" +
                            "&showInformation=1" +
                            "&showPrimaryStar=1" +
                            if (size > 0) {
                                "&size=$size"
                            } else {
                            }
                }
            }
        }
        val connection = URL(url).openConnection()
        connection.doInput = true
        val json = "{systems:" + connection.getInputStream().bufferedReader().readText() + "}"
        val jsonArray = JsonParser().parse(json).asJsonObject.getAsJsonArray("systems")
        val systems = ArrayList<System>()
        jsonArray.forEach {
            systems.add(Gson().fromJson<System>(it.asJsonObject, System::class.java))
        }
        return systems
    }

    fun findSystemsByName(name: String): ArrayList<System> {
        return findSystems(SearchType.NAME, name)
    }

    fun findSystemsInSphere(initialSystem: String = "", x: Double = 0.0, y: Double = 0.0, z: Double = 0.0, radius: Int = 0): ArrayList<System> {
        return findSystems(SearchType.SPHERE, initialSystem, x, y, z, radius)
    }

    fun findSystemsInCube(initialSystem: String = "", x: Double = 0.0, y: Double = 0.0, z: Double = 0.0, size: Int = 0): ArrayList<System> {
        return findSystems(SearchType.CUBE, initialSystem, x, y, z, size)
    }

    fun getBodies(system: String): System {
        val name = URLEncoder.encode(system, "UTF-8")
        val connection = URL(BASE_URL + BODIES +
                "/?systemName=$name").openConnection()
        connection.doInput = true
        val json = connection.inputStream.bufferedReader().readText()
        return Gson().fromJson<System>(json, System::class.java)
    }

    fun getBodies(system: System) {
        System.updateSystem(system, getBodies(system.name!!))
    }

    fun getFactions(system: String): System {
        val name = URLEncoder.encode(system, "UTF-8")
        val connection = URL(BASE_URL + FACTIONS +
                "/?systemName=$name").openConnection()
        connection.doInput = true
        val json = connection.inputStream.bufferedReader().readText()
        return Gson().fromJson<System>(json, System::class.java)
    }

    fun getFactions(system: System) {
        System.updateSystem(system, getFactions(system.name!!))
    }

    fun getStations(system: String): System {
        val name = URLEncoder.encode(system, "UTF-8")
        val connection = URL(BASE_URL + STATIONS +
                "/?systemName=$name").openConnection()
        connection.doInput = true
        val json = connection.inputStream.bufferedReader().readText()
        return Gson().fromJson<System>(json, System::class.java)
    }

    fun getStations(system: System) {
        System.updateSystem(system, getStations(system.name!!))
    }

    fun checkApiKey(): Boolean {
        val connection = URL(BASE_URL + GET_RANKS +
                "/?commanderName=${URLEncoder.encode(commander, "UTF-8")}" +
                "&apiKey=$apiKey").openConnection()
        connection.doInput = true
        val json = connection.getInputStream().bufferedReader().readText()
        val msgnum = JsonParser().parse(json).asJsonObject["msgnum"].asInt
        return msgnum == 100
    }

    private fun set(baseUrl: String, query: String) {
        val connection = URL(baseUrl +
                "/?commanderName=${URLEncoder.encode(commander, "UTF-8")}" +
                "&apiKey=$apiKey"
        ).openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.doOutput = true
        connection.doInput = true
        DataOutputStream(connection.outputStream).writeBytes(query)
        connection.connect()
        val json = connection.inputStream.bufferedReader().readText()
        val jsonObject = JsonParser().parse(json).asJsonObject
        logger.debug { jsonObject["msgnum"].asString + " " + jsonObject["msg"].asString }
    }

    fun setLog(jump: FSDJump) {
        val x = jump.StarPos!!.elementAt(0)
        val y = jump.StarPos!!.elementAt(1)
        val z = jump.StarPos!!.elementAt(2)
        val query = "fromSoftware=$FROM_SOFTWARE" +
                "&fromSoftwareVersion=$FROM_SOFTWARE_VERSION" +
                "&x=$x" +
                "&y=$y" +
                "&z=$z" +
                "&systemName=${URLEncoder.encode(jump.StarSystem, "UTF-8")}" +
                "&dateVisited=${URLEncoder.encode(jump.eventTimeUTC().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), "UTF-8")}"
        set(BASE_URL + SET_LOG, query)
    }

    fun getLog(endDate: String = ""): MutableMap<String, Any> {
        val connection = URL(BASE_URL + GET_LOGS +
                "/?commanderName=${URLEncoder.encode(commander, "UTF-8")}" +
                "&apiKey=$apiKey" +
                if (endDate.isNotEmpty()) {
                    "&endDate=${URLEncoder.encode(endDate, "UTF-8")}"
                } else {
                    ""
                }).openConnection()
        val json = connection.getInputStream().bufferedReader().readText()
        val jsonObject = JsonParser().parse(json).asJsonObject
        return mutableMapOf(
                "startDateTime" to jsonObject["startDateTime"].asString,
                "logs" to jsonObject["logs"].asJsonArray
        )
    }

    fun getLastLogDate(): String {
        var s: MutableMap<String, Any>
        var found = false
        var endDate = ""
        var logDate = ""
        while (!found) {
            s = getLog(endDate)
            if (s.containsKey("logs")) {
                if ((s["logs"] as JsonArray).size() > 0) {
                    val type = object : TypeToken<ArrayList<Log>>() {}.type
                    val logs: ArrayList<Log> = Gson().fromJson(s["logs"] as JsonArray, type)
                    logs.sortByDescending { it.date() }
                    logDate = logs.first().date
                    found = true
                } else {
                    endDate = s["startDateTime"] as String
                }
            }
        }
        return logDate
    }

    fun setCredits(credits: Long, loan: Long) {
        val query = "balance=$credits" +
                "&loan=$loan"
        set(BASE_URL + SET_CREDITS, query)
    }

    fun setRanks(ranks: Rank) {
        val query = "Combat=${ranks.Combat}" +
                "&Trade=${ranks.Trade}" +
                "&Explore=${ranks.Explore}" +
                "&CQC=${ranks.CQC}" +
                "&Federation=${ranks.Federation}" +
                "&Empire=${ranks.Empire}"
        set(BASE_URL + GET_LOGS, query)
    }

    fun setMaterials(materials: Materials) {
        val mats = materials.Manufactured!!
        mats.addAll(materials.Raw!!)
        val map = mutableMapOf<String, Int>()
        mats.forEach { it ->
            map[it.Name!!] = it.Count!!
        }
        val query = "type=materials" +
                "&values=${Gson().toJson(map)}"
        set(BASE_URL + SET_MATS, query)
    }

    fun setData(materials: Materials) {
        val map = mutableMapOf<String, Int>()
        materials.Encoded!!.forEach {
            map[it.Name!!] = it.Count!!
        }
        val query = "type=data" +
                "&values=${Gson().toJson(map)}"
        set(BASE_URL + SET_MATS, query)
    }

    fun updateShip(ship: ShipyardSwap) {
        val query = "shipId=${ship.ShipID}" +
                "&shipName=${ship.ShipFD}" +
                "&type=${ship.ShipType}"

        set(BASE_URL + UPDATE_SHIP, query)
    }

    fun sellShip(ship: ShipyardSell) {
        val query = "shipId=${ship.SellShipId}"
        set(BASE_URL + SELL_SHIP, query)
    }

    override fun getEntries(entries: ArrayList<JournalEntry>) {
        if (apiKey.isNotEmpty()) {
            entries.forEach {
                when (it) {
                    is FSDJump -> {
                        setLog(it)
                    }
                    is LoadGame -> {
                        setCredits(it.Credits!!, it.Loan!!)
                    }
                    is Rank -> {
                        setRanks(it)
                    }
                    is Materials -> {
                        setMaterials(it)
                        setData(it)
                    }
                    is ShipyardSwap -> {
                        updateShip(it)
                    }
                    is ShipyardSell -> {
                        sellShip(it)
                    }
                }
            }
        }
    }
}