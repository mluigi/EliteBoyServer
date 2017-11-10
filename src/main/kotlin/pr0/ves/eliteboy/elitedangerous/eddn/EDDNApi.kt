package pr0.ves.eliteboy.elitedangerous.eddn

import com.google.gson.*
import mu.KLogging
import pr0.ves.eliteboy.elitedangerous.companionapi.EDCompanionApi
import pr0.ves.eliteboy.elitedangerous.companionapi.data.Market
import pr0.ves.eliteboy.elitedangerous.companionapi.data.Shipyard
import pr0.ves.eliteboy.elitedangerous.journal.Journal
import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry
import pr0.ves.eliteboy.elitedangerous.journal.events.*
import pr0.ves.eliteboy.elitedangerous.journal.events.deserializers.IngredientsDeserializer
import pr0.ves.eliteboy.elitedangerous.journal.events.deserializers.ScanDeserializer
import pr0.ves.eliteboy.elitedangerous.journal.events.util.BlueprintIngredients
import pr0.ves.eliteboy.server.Commander
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

    var lastStarSystem = ""
    var lastStarPos: List<Double> = ArrayList()
    var lastStation = ""

    var edApi: EDCompanionApi? = null

    val gson = GsonBuilder()
            .setExclusionStrategies(object : ExclusionStrategy {
                override fun shouldSkipField(f: FieldAttributes): Boolean {
                    return f.name.equals("journal", true)
                }

                override fun shouldSkipClass(aClass: Class<*>): Boolean {
                    return false
                }
            })
            .setPrettyPrinting()
            .registerTypeAdapter(Scan::class.java, ScanDeserializer())
            .registerTypeAdapter(BlueprintIngredients::class.java, IngredientsDeserializer())
            .create()!!

    fun headers(): JsonObject {
        val jsonObject = JsonObject()

        jsonObject.addProperty("uploaderID", commander)
        jsonObject.addProperty("softwareName", fromSoftware)
        jsonObject.addProperty("softwareVersion", fromSoftwareVersion)
        return jsonObject
    }

    fun sendData(data: String) {
        val connection = URL(UPLOAD).openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.setRequestProperty("Content-Type", "application/json")
        connection.doOutput = true
        connection.doInput = true
        DataOutputStream(connection.outputStream).writeBytes(data)
        connection.connect()
        logger.info { "${connection.responseCode} ${connection.responseMessage}" }
    }

    fun message(jump: FSDJump): JsonObject {
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

    fun message(dock: Docked, starpos: List<Double>): JsonObject {
        val message = gson.toJsonTree(dock).asJsonObject
        removeLocalFields(message)
        message.remove("CockpitBreach")
        message.add("StarPos", gson.toJsonTree(starpos))
        message.entrySet().removeIf { it.key.contains("Localised") }
        return message
    }

    fun message(scan: Scan, starSystem: String, starpos: List<Double>): JsonObject {
        val message = gson.toJsonTree(scan).asJsonObject
        removeLocalFields(message)
        message.entrySet().removeIf { it.key.contains("Localised") }
        message.add("StarPos", gson.toJsonTree(starpos))
        message.addProperty("StarSystem", starSystem)
        message.remove("AbsoluteMagnitude")
        message.remove("nSurfaceTemperature")
        message.remove("StellarMass")
        message.remove("Age_MY")
        message.remove("nRotationPeriod")
        return message
    }

    fun message(location: Location): JsonObject {
        val message = gson.toJsonTree(location).asJsonObject
        removeLocalFields(message)
        message.entrySet().removeIf { it.key.contains("Localised") }
        message.remove("Latitude")
        message.remove("Longitude")
        return message
    }

    fun message(market: Market, starSystem: String): JsonObject {
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

    fun message(shipyard: Shipyard, starSystem: String): JsonObject {
        val message = gson.toJsonTree(shipyard).asJsonObject
        removeLocalFields(message)
        message.remove("economies")
        message.remove("exported")
        message.remove("id")
        message.remove("imported")
        message.remove("outpostType")
        message.remove("services")
        var array = JsonArray()
        message["modules"].asJsonObject.entrySet().forEach { array.add(it.value.asJsonObject["name"]) }
        message.remove("modules")
        message.add("modules", array)

        array = JsonArray()
        message["ships"].asJsonObject["shipyard_list"].asJsonObject.entrySet().forEach { array.add(it.key) }
        val un = message["ships"].asJsonObject["unavailable_list"]
        if (!un.isJsonArray) {
            un.asJsonObject.entrySet().forEach { array.add(it.key) }
        }
        message.remove("ships")
        message.add("ships", array)

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

    fun removeLocalFields(obj: JsonObject) {
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

/*fun main(args: Array<String>) {
    val commander = Commander.fromFile("Pr0ves")
    val api = EDDNApi(commander.name)
    val edapi = EDCompanionApi(commander)
    api.edApi = edapi
    api.edApi!!.login()
    val journal = Journal()
    val dock = "{ \"timestamp\":\"2017-11-09T12:22:21Z\", \"event\":\"Docked\", \"StationName\":\"Patterson Dock\", \"StationType\":\"Orbis\", \"StarSystem\":\"Uszaa\", \"StationFaction\":\"Orrere Energy Company\", \"StationGovernment\":\"\$government_Corporate;\", \"StationGovernment_Localised\":\"Corporate\", \"StationAllegiance\":\"Federation\", \"StationServices\":[ \"Dock\", \"Autodock\", \"BlackMarket\", \"Commodities\", \"Contacts\", \"Exploration\", \"Missions\", \"Outfitting\", \"CrewLounge\", \"Rearm\", \"Refuel\", \"Repair\", \"Shipyard\", \"Tuning\", \"MissionsGenerated\", \"Facilitator\", \"FlightController\", \"StationOperations\", \"Powerplay\", \"SearchAndRescue\" ], \"StationEconomy\":\"\$economy_Industrial;\", \"StationEconomy_Localised\":\"Industrial\", \"DistFromStarLS\":1232.918091 }\n"
    val docked = journal.getJournalEntryTyped(dock)
    val fsd = "{ \"timestamp\":\"2017-11-09T12:17:47Z\", \"event\":\"FSDJump\", \"StarSystem\":\"Uszaa\", \"StarPos\":[68.844,48.750,74.750], \"SystemAllegiance\":\"Federation\", \"SystemEconomy\":\"\$economy_Industrial;\", \"SystemEconomy_Localised\":\"Industrial\", \"SystemGovernment\":\"\$government_Corporate;\", \"SystemGovernment_Localised\":\"Corporate\", \"SystemSecurity\":\"\$SYSTEM_SECURITY_low;\", \"SystemSecurity_Localised\":\"Low Security\", \"Population\":3199999988, \"Powers\":[ \"Edmund Mahon\" ], \"PowerplayState\":\"Exploited\", \"JumpDist\":17.790, \"FuelUsed\":2.858732, \"FuelLevel\":78.619148, \"Factions\":[ { \"Name\":\"Pilots Federation Local Branch\", \"FactionState\":\"None\", \"Government\":\"Democracy\", \"Influence\":0.000000, \"Allegiance\":\"PilotsFederation\" }, { \"Name\":\"Uszaa Society\", \"FactionState\":\"Boom\", \"Government\":\"Anarchy\", \"Influence\":0.039039, \"Allegiance\":\"Independent\" }, { \"Name\":\"Uszaa Jet Galactic & Co\", \"FactionState\":\"CivilWar\", \"Government\":\"Corporate\", \"Influence\":0.063063, \"Allegiance\":\"Independent\" }, { \"Name\":\"Orrere Energy Company\", \"FactionState\":\"None\", \"Government\":\"Corporate\", \"Influence\":0.549550, \"Allegiance\":\"Federation\", \"RecoveringStates\":[ { \"State\":\"Boom\", \"Trend\":1 } ] }, { \"Name\":\"Union of Arexe Future\", \"FactionState\":\"Boom\", \"Government\":\"Democracy\", \"Influence\":0.071071, \"Allegiance\":\"Federation\" }, { \"Name\":\"Worker's Party of Uszaa\", \"FactionState\":\"Boom\", \"Government\":\"Communism\", \"Influence\":0.050050, \"Allegiance\":\"Independent\" }, { \"Name\":\"Liberty Party of Uszaa\", \"FactionState\":\"CivilWar\", \"Government\":\"Dictatorship\", \"Influence\":0.069069, \"Allegiance\":\"Independent\" }, { \"Name\":\"Uszaa Gold Travel Group\", \"FactionState\":\"Boom\", \"Government\":\"Corporate\", \"Influence\":0.158158, \"Allegiance\":\"Federation\" } ], \"SystemFaction\":\"Orrere Energy Company\" }\n"
    val fsdjump = journal.getJournalEntryTyped(fsd)
    api.getEntries(ArrayList<JournalEntry>().also {
        it.add(fsdjump)
        it.add(docked)
    })
}*/