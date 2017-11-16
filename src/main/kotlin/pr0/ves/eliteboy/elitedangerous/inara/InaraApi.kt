package pr0.ves.eliteboy.elitedangerous.inara

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import mu.KLogging
import pr0.ves.eliteboy.elitedangerous.companionapi.EDCompanionApi
import pr0.ves.eliteboy.elitedangerous.companionapi.data.Profile
import pr0.ves.eliteboy.elitedangerous.eddn.EDDNApi
import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry
import pr0.ves.eliteboy.elitedangerous.journal.events.*
import pr0.ves.eliteboy.server.listeners.JournalWatcherListener
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit

class InaraApi(val commander: String, var apiKey: String = "") : JournalWatcherListener {
    companion object : KLogging() {
        val BASE_URL = "https://inara.cz/inapi/v1/"
    }

    var events = ArrayList<JsonObject>()

    var edApi: EDCompanionApi? = null
    private var lastStarSystem = ""
    private var lastStation = ""
    private var lastShip = ""
    private var lastShipID = 0


    override fun getEntries(entries: ArrayList<JournalEntry>) {
        if (apiKey.isNotEmpty()) {
            entries.forEach {
                when (it) {
                    is LoadGame -> {
                        lastShip = it.Ship!!
                        lastShipID = it.ShipID!!
                        events.add(setCommanderCreditsEvent(it))
                        val profile = edApi!!.getProfile()
                        lastStarSystem = profile.lastSystem!!.name!!
                        lastStation = profile.lastStarport!!.name!!
                    }
                    is FSDJump -> {
                        lastStarSystem = it.StarSystem!!
                    }
                    is Docked -> {
                        lastStation = it.StationName!!
                    }
                    is ShipyardSell -> {
                    }
                    is ShipyardBuy -> {
                    }
                    is ShipyardSwap -> {
                    }
                    is MissionAccepted -> {
                    }
                    is MissionCompleted -> {
                    }
                    is MissionAbandoned -> {
                    }
                    is MissionFailed -> {
                    }
                    is Died -> {
                    }
                    is Interdiction -> {
                    }
                    is Interdicted -> {
                    }
                    is EscapeInterdiction -> {
                    }
                    is PVPKill -> {
                    }
                    is CommunityGoal -> {
                    }
                    is EngineerProgress -> {

                    }
                    is Rank -> {

                    }
                    is Cargo -> {

                    }
                    is Materials -> {

                    }
                }
            }
        }
    }

    private fun headers(): JsonObject {
        val jsonObject = JsonObject()

        jsonObject.addProperty("commanderName", commander)
        jsonObject.addProperty("appName", EDDNApi.fromSoftware)
        jsonObject.addProperty("appVersion", EDDNApi.fromSoftwareVersion)
        jsonObject.addProperty("APIkey", apiKey)
        jsonObject.addProperty("isDeveloped", true)
        return jsonObject
    }

    fun now() = OffsetDateTime.now(ZoneId.of("UTC")).truncatedTo(ChronoUnit.SECONDS).toString()

    fun sendData(data: JsonObject) {
    }

    fun setCommanderCreditsEvent(profile: Profile): JsonObject {
        val event = JsonObject()
        event.addProperty("eventName", "setCommanderCredits")
        event.addProperty("eventTimestamp", OffsetDateTime.now().toString())
        event.add("eventData", JsonObject().also {
            it.addProperty("commanderCredits", profile.commander!!.credits)
            it.addProperty("commanderLoan", profile.commander!!.debt)
        })
        return event
    }

    fun setCommanderCreditsEvent(load: LoadGame): JsonObject {
        val event = JsonObject()
        event.addProperty("eventName", "setCommanderCredits")
        event.addProperty("eventTimestamp", OffsetDateTime.now().toString())
        event.add("eventData", JsonObject().also {
            it.addProperty("commanderCredits", load.Credits)
            it.addProperty("commanderLoan", load.Loan)
        })
        return event
    }

    fun setCommanderRankEngineerEvent(progress: EngineerProgress): JsonObject {
        val event = JsonObject()
        event.addProperty("eventName", "setCommanderRankEngineer")
        event.addProperty("eventTimestamp", progress.timestamp)
        event.add("eventData", JsonObject().also {
            it.addProperty("engineerName", progress.Engineer!!)
            it.addProperty("rankValue", progress.Rank)
        })
        return event
    }

    fun setCommanderRankPilotEvent(name: String, rank: Int): JsonObject {
        val event = JsonObject()
        event.addProperty("eventName", "setCommanderRankEngineer")
        event.addProperty("eventTimestamp", now())
        event.add("eventData", JsonObject().also {
            it.addProperty("rankName", name)
            it.addProperty("rankValue", rank)
        })
        return event
    }

    fun setCommanderInventoryCargoEvent(cargo: Cargo): JsonObject {
        val event = JsonObject()
        event.addProperty("eventName", "setCommanderInventoryCargo")
        event.addProperty("eventTimestamp", cargo.timestamp)
        event.add("eventData", JsonArray().also {
            cargo.Inventory!!.forEach { item ->
                it.add(JsonObject().also { obj ->
                    obj.addProperty("itemName", item.Name)
                    obj.addProperty("itemCount", item.Count)
                })
            }
        })
        return event
    }

    fun setCommanderInventoryMaterialsEvent(materials: Materials): JsonObject {
        val event = JsonObject()
        event.addProperty("eventName", "setCommanderInventoryMaterials")
        event.addProperty("eventTimestamp", materials.timestamp)
        event.add("eventData", JsonArray().also {
            materials.Encoded!!.forEach { item ->
                it.add(JsonObject().also { obj ->
                    obj.addProperty("itemName", item.Name)
                    obj.addProperty("itemCount", item.Count)
                })
            }
            materials.Manufactured!!.forEach { item ->
                it.add(JsonObject().also { obj ->
                    obj.addProperty("itemName", item.Name)
                    obj.addProperty("itemCount", item.Count)
                })
            }
            materials.Raw!!.forEach { item ->
                it.add(JsonObject().also { obj ->
                    obj.addProperty("itemName", item.Name)
                    obj.addProperty("itemCount", item.Count)
                })
            }
        })
        return event
    }

    fun addCommanderShipEvent(ship: ShipyardNew): JsonObject {
        val event = JsonObject()
        event.addProperty("eventName", "addCommanderShip")
        event.addProperty("eventTimestamp", ship.timestamp)
        event.add("eventData", JsonObject().also {
            it.addProperty("shipType", ship.ShipType)
            it.addProperty("shipGameID", ship.NewShipId)
        })
        return event
    }

    fun delCommanderShipEvent(ship: ShipyardSell): JsonObject {
        val event = JsonObject()
        event.addProperty("eventName", "delCommanderShip")
        event.addProperty("eventTimestamp", ship.timestamp)
        event.add("eventData", JsonObject().also {
            it.addProperty("shipType", ship.ShipType)
            it.addProperty("shipGameID", ship.SellShipId)
        })
        return event
    }

    fun delCommanderShipEvent(ship: ShipyardBuy): JsonObject {
        val event = JsonObject()
        event.addProperty("eventName", "delCommanderShip")
        event.addProperty("eventTimestamp", ship.timestamp)
        event.add("eventData", JsonObject().also {
            it.addProperty("shipType", ship.SellOldShip)
            it.addProperty("shipGameID", ship.SellShipId)
        })
        return event
    }

    fun delCommanderShipEvent(ship: ShipyardSwap): JsonObject {
        val event = JsonObject()
        event.addProperty("eventName", "delCommanderShip")
        event.addProperty("eventTimestamp", ship.timestamp)
        event.add("eventData", JsonObject().also {
            it.addProperty("shipType", ship.SellOldShip)
            it.addProperty("shipGameID", ship.SellShipId)
        })
        return event
    }

    fun setCommanderShipEvent(ship: SetUserShipName): JsonObject {
        val event = JsonObject()
        event.addProperty("eventName", "setCommanderShip")
        event.addProperty("eventTimestamp", ship.timestamp)
        event.add("eventData", JsonObject().also {
            it.addProperty("shipType", ship.Ship)
            it.addProperty("shipGameID", ship.ShipID)
            it.addProperty("shipName", ship.ShipName)
            it.addProperty("shipIdent", ship.ShipIdent)
        })
        return event
    }

    fun setCommanderShipEventStore(ship: ShipyardSwap): ArrayList<JsonObject> {
        val store = JsonObject()
        store.addProperty("eventName", "setCommanderShip")
        store.addProperty("eventTimestamp", ship.timestamp)
        store.add("eventData", JsonObject().also {
            it.addProperty("shipType", ship.StoreOldShip)
            it.addProperty("shipGameID", ship.StoreShipId)
            it.addProperty("starsystemName", lastStarSystem)
            it.addProperty("stationName", lastStation)
        })
        val evs = ArrayList<JsonObject>()
        evs.add(store)
        evs.add(setCommanderShipEvent(ship))
        return evs
    }

    fun setCommanderShipEvent(ship: ShipyardSwap): JsonObject {
        val event = JsonObject()
        event.addProperty("eventName", "setCommanderShip")
        event.addProperty("eventTimestamp", ship.timestamp)
        event.add("eventData", JsonObject().also {
            it.addProperty("shipType", ship.ShipType)
            it.addProperty("shipGameID", ship.StoreShipId)
            it.addProperty("isCurrentShip", true)
        })
        return event
    }

    fun setCommanderShipEvent(profile: Profile): JsonObject {
        val event = JsonObject()
        event.addProperty("eventName", "setCommanderShip")
        event.addProperty("eventTimestamp", now())
        event.add("eventData", JsonObject().also {
            it.addProperty("shipType", profile.ship!!.name)
            it.addProperty("shipHullValue", profile.ship!!.value!!.hull)
            it.addProperty("shipModulesValue", profile.ship!!.value!!.modules)
            it.addProperty("starsystemName", profile.ship!!.starsystem!!.name)
            it.addProperty("stationName", profile.ship!!.station!!.name)
            it.addProperty("isCurrentShip", true)
        })
        return event
    }

    fun setCommanderShipTransferEvent(ship: ShipyardTransfer): JsonObject {
        val event = JsonObject()
        event.addProperty("eventName", "setCommanderShipTransfer")
        event.addProperty("eventTimestamp", ship.timestamp)
        event.add("eventData", JsonObject().also {
            it.addProperty("shipType", ship.ShipType)
            it.addProperty("shipGameID", ship.ShipID)
            it.addProperty("starsystemName", lastStarSystem)
            it.addProperty("stationName", lastStation)
            it.addProperty("transferTime", ship.nTransferTime)
        })

        return event
    }

    fun addCommanderTravelDockEvent(dock: Docked): JsonObject {
        val event = JsonObject()
        event.addProperty("eventName", "addCommanderTravelDock")
        event.addProperty("eventTimestamp", dock.timestamp)
        event.add("eventData", JsonObject().also {
            it.addProperty("starsystemName", dock.StarSystem)
            it.addProperty("stationName", dock.StationName)
            it.addProperty("shipType", lastShip)
            it.addProperty("shipGameID", lastShipID)
        })
        return event
    }
}