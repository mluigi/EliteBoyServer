package pr0.ves.eliteboy.elitedangerous.eddn

import com.google.gson.*
import mu.KLogging
import pr0.ves.eliteboy.elitedangerous.companionapi.EDCompanionApi
import pr0.ves.eliteboy.elitedangerous.companionapi.data.Market
import pr0.ves.eliteboy.elitedangerous.companionapi.data.Shipyard
import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry
import pr0.ves.eliteboy.elitedangerous.journal.events.*
import pr0.ves.eliteboy.elitedangerous.journal.events.deserializers.IngredientsDeserializer
import pr0.ves.eliteboy.elitedangerous.journal.events.deserializers.ScanDeserializer
import pr0.ves.eliteboy.elitedangerous.journal.events.util.BlueprintIngredients
import pr0.ves.eliteboy.server.listeners.JournalWatcherListener
import java.io.DataOutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.time.ZoneOffset
import java.time.ZonedDateTime

class EDDNApi(var commander: String = "") : JournalWatcherListener {
    companion object : KLogging() {
        val UPLOAD = "https://eddn.edcd.io:4430/upload/"
        val COMMODITY_REF = "https://eddn.edcd.io/schemas/commodity/3"
        val JOURNAL_REF = "https://eddn.edcd.io/schemas/journal/1"
        val OUTFITTING_REF = "https://eddn.edcd.io/schemas/outfitting/2"
        val SHIPYARD_REF = "https://eddn.edcd.io/schemas/shipyard/2"
        val BLACKMARKET_REF = "https://eddn.edcd.io/schemas/blackmarket/1"
        val fromSoftware = "EliteBoy"
        val fromSoftwareVersion = "1.0"
    }

    private var lastStarSystem = ""
    private var lastStarPos: List<Double> = ArrayList()
    private var lastStation = ""

    var edApi: EDCompanionApi? = null

    val gson = GsonBuilder()
            .setExclusionStrategies(object : ExclusionStrategy {
                override fun shouldSkipField(f: FieldAttributes): Boolean = f.name.equals("journal", true)
                override fun shouldSkipClass(aClass: Class<*>): Boolean = false
            })
            .setPrettyPrinting()
            .registerTypeAdapter(Scan::class.java, ScanDeserializer())
            .registerTypeAdapter(BlueprintIngredients::class.java, IngredientsDeserializer())
            .create()!!

    private fun headers(): JsonObject {
        val jsonObject = JsonObject()

        jsonObject.addProperty("uploaderID", commander)
        jsonObject.addProperty("softwareName", fromSoftware)
        jsonObject.addProperty("softwareVersion", fromSoftwareVersion)
        return jsonObject
    }

    private fun sendData(data: String) {
        val connection = URL(UPLOAD).openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.setRequestProperty("Content-Type", "application/json")
        connection.doOutput = true
        connection.doInput = true
        DataOutputStream(connection.outputStream).writeBytes(data)
        connection.connect()
        logger.debug { "${connection.responseCode} ${connection.responseMessage}" }
    }

    private fun message(jump: FSDJump): JsonObject {
        val message = gson.toJsonTree(jump).asJsonObject
        lastStarPos = jump.StarPos!!.toList()
        removeLocalFields(message)
        message.remove("BoostUsed")
        message.remove("JumpDist")
        message.remove("FuelUsed")
        message.remove("FuelLevel")
        message.entrySet().removeIf { it.key.contains("Localised") }
        return message
    }

    private fun message(dock: Docked, starpos: List<Double>): JsonObject {
        val message = gson.toJsonTree(dock).asJsonObject
        removeLocalFields(message)
        message.remove("CockpitBreach")
        message.add("StarPos", gson.toJsonTree(starpos))
        message.entrySet().removeIf { it.key.contains("Localised") }
        return message
    }

    private fun message(scan: Scan, starSystem: String, starpos: List<Double>): JsonObject {
        val message = gson.toJsonTree(scan).asJsonObject
        removeLocalFields(message)
        message.entrySet().removeIf { it.key.contains("Localised") }
        message.add("StarPos", gson.toJsonTree(starpos))
        message.addProperty("StarSystem", starSystem)
        return message
    }

    private fun message(location: Location): JsonObject {
        val message = gson.toJsonTree(location).asJsonObject
        removeLocalFields(message)
        message.entrySet().removeIf { it.key.contains("Localised") }
        message.remove("Latitude")
        message.remove("Longitude")
        return message
    }

    private fun message(market: Market, starSystem: String): JsonObject {
        val message = gson.toJsonTree(market).asJsonObject
        removeLocalFields(message)
        message.remove("exported")
        message.remove("imported")
        message.remove("outpostType")
        message.remove("services")
        message["commodities"].asJsonArray.toMutableList().removeIf {
            it.asJsonObject["categoryname"].asString == "NonMarketable" ||
                    it.asJsonObject["legality"].asString.isNotEmpty()
        }
        message["commodities"].asJsonArray.toMutableList().forEach {
            it.asJsonObject.remove("categoryname")
            it.asJsonObject.remove("id")
            it.asJsonObject.remove("legality")
            it.asJsonObject.remove("locName")
            it.asJsonObject.remove("statusFlags")
        }
        var array = JsonArray()
        message["economies"].asJsonObject.entrySet().forEach { array.add(it.value) }
        message.remove("economies")
        message.add("economies", array)
        array = JsonArray()
        message["prohibited"].asJsonObject.entrySet().forEach { array.add(it.value) }
        message.remove("prohibited")
        message.add("prohibited", array)
        message.addProperty("stationName", message["name"].asString)
        message.remove("name")
        message.addProperty("systemName", starSystem)
        message.addProperty("timestamp", ZonedDateTime.now(ZoneOffset.UTC).toString())
        return message
    }

    private fun message(shipyard: Shipyard, starSystem: String): JsonObject {
        val message = gson.toJsonTree(shipyard).asJsonObject
        removeLocalFields(message)
        message.remove("economies")
        message.remove("exported")
        message.remove("id")
        message.remove("imported")
        message.remove("outpostType")
        message.remove("services")
        var array = JsonArray()

        if (message.has("modules")) {
            message["modules"].asJsonObject.entrySet().forEach { array.add(it.value.asJsonObject["name"]) }
            message.remove("modules")
            message.add("modules", array)
        }

        if (message.has("ships")) {
            array = JsonArray()
            message["ships"].asJsonObject["shipyard_list"].asJsonObject.entrySet().forEach { array.add(it.key) }
            val un = message["ships"].asJsonObject["unavailable_list"]
            if (!un.isJsonArray) {
                un.asJsonObject.entrySet().forEach { array.add(it.key) }
            }
            message.remove("ships")
            message.add("ships", array)
        }

        message.addProperty("stationName", message["name"].asString)
        message.remove("name")
        message.addProperty("systemName", starSystem)
        message.addProperty("timestamp", ZonedDateTime.now(ZoneOffset.UTC).toString())

        return message
    }

    fun message(marketSell: MarketSell, starSystem: String, station: String): JsonObject {
        val message = gson.toJsonTree(marketSell).asJsonObject
        removeLocalFields(message)
        message.remove("FriendlyType")
        message.addProperty("systemName", starSystem)
        message.addProperty("stationName", station)

        return message
    }

    private fun removeLocalFields(obj: JsonObject) {
        obj.remove("id")
        obj.remove("nLine")
    }


    override fun getEntries(entries: ArrayList<JournalEntry>) {
        entries.forEach {
            when (it) {
                is FSDJump -> {
                    lastStarPos = it.StarPos!!.toList()
                    lastStarSystem = it.StarSystem!!
                    val json = JsonObject()
                    json.addProperty("\$schemaRef", JOURNAL_REF)
                    json.add("header", headers())
                    json.add("message", message(it))
                    sendData(json.toString())

                }
                is Docked -> {
                    lastStation = it.StationName!!
                    lastStarSystem = it.StarSystem!!
                    if (lastStarPos.isNotEmpty()) {
                        val json = JsonObject()
                        json.addProperty("\$schemaRef", JOURNAL_REF)
                        json.add("header", headers())
                        json.add("message", message(it, lastStarPos))
                        sendData(json.toString())

                        if (edApi != null) {
                            if (edApi!!.currentState == EDCompanionApi.State.READY) {
                                val profile = edApi!!.getProfile()
                                if (profile.lastSystem!!.name == lastStarSystem) {
                                    val market = edApi!!.getMarket()
                                    val shipyard = edApi!!.getShipyard()

                                    json.addProperty("\$schemaRef", COMMODITY_REF)
                                    json.add("message", message(market, lastStarSystem))
                                    sendData(json.toString())

                                    val shipyardMessage = message(shipyard, lastStarSystem)
                                    val outfittng = shipyardMessage.deepCopy()
                                    shipyardMessage.remove("modules")
                                    outfittng.remove("ships")

                                    json.addProperty("\$schemaRef", SHIPYARD_REF)
                                    json.add("message", shipyardMessage)
                                    sendData(json.toString())

                                    json.addProperty("\$schemaRef", OUTFITTING_REF)
                                    json.add("message", outfittng)
                                    sendData(json.toString())
                                }
                            }
                        }
                    }
                }
                is Scan -> {
                    if (lastStarPos.isNotEmpty() && lastStarSystem.isNotEmpty()) {
                        val json = JsonObject()
                        json.addProperty("\$schemaRef", JOURNAL_REF)
                        json.add("header", headers())
                        json.add("message", message(it, lastStarSystem, lastStarPos))
                        sendData(json.toString())
                    }
                }
                is Location -> {
                    lastStarSystem = it.StarSystem!!
                    lastStarPos = it.StarPos!!.toList()
                    val json = JsonObject()
                    json.addProperty("\$schemaRef", JOURNAL_REF)
                    json.add("header", headers())
                    json.add("message", message(it))
                    sendData(json.toString())
                }
                is MarketSell -> {
                    if (lastStation.isNotEmpty()) {
                        val json = JsonObject()
                        json.addProperty("\$schemaRef", BLACKMARKET_REF)
                        json.add("header", headers())
                        json.add("message", message(it, lastStarSystem, lastStation))
                        sendData(json.toString())
                    }
                }
            }
        }
    }
}
/*
fun main(args: Array<String>) {
    val commander = Commander.fromFile("Pr0ves")
    val edapi = EDCompanionApi(commander)
    edapi.login()
    val kk = edapi.getMarket()
    true
}*/