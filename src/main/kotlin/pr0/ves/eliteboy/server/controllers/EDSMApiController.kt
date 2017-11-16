package pr0.ves.eliteboy.server.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pr0.ves.eliteboy.elitedangerous.edsm.EDSMApi
import pr0.ves.eliteboy.elitedangerous.edsm.data.System
import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry
import pr0.ves.eliteboy.elitedangerous.journal.events.FSDJump
import pr0.ves.eliteboy.elitedangerous.journal.events.LoadGame
import pr0.ves.eliteboy.elitedangerous.journal.events.Materials
import pr0.ves.eliteboy.elitedangerous.journal.events.Rank
import pr0.ves.eliteboy.server.listeners.JournalWatcherListener
import pr0.ves.eliteboy.server.services.CommanderService


@RestController
@RequestMapping("/api/cmdr/edsm")
class EDSMApiController {

    @Autowired
    lateinit var service: CommanderService

    @RequestMapping("/setApiKey")
    fun setApiKey(
            @RequestParam(required = true) cmdr: String,
            @RequestParam(required = true) apiKey: String
    ): MutableMap<String, String> {
        service.commander.edsmApiKey = apiKey
        service.commander.edsmCommander = cmdr
        service.commander.toFile()
        service.edsmApi = EDSMApi(cmdr, apiKey)
        return if (service.edsmApi.checkApiKey()) {
            mutableMapOf(
                    "code" to "200",
                    "msg" to "ApiKey set"
            )
        } else {
            mutableMapOf(
                    "code" to "404",
                    "msg" to "Error: ApiKey not correct"
            )
        }

    }

    @RequestMapping("/getSystem")
    fun getSystem(@RequestParam system: String): System {
        val api = service.edsmApi
        val sys = api.getSystem(system)
        api.getBodies(sys)
        api.getStations(sys)
        api.getFactions(sys)
        return sys
    }

    @RequestMapping("/findSystemsByName")
    fun findSystemsByName(
            @RequestParam name: String
    ): MutableMap<String, ArrayList<System>> {
        return mutableMapOf(
                "systems" to service.edsmApi.findSystemsByName(name)
        )
    }

    @RequestMapping("/findSystemInSphere")
    fun findSystemInSphere(
            @RequestParam(required = false, defaultValue = "") system: String,
            @RequestParam(required = false, defaultValue = "0.0") x: Double,
            @RequestParam(required = false, defaultValue = "0.0") y: Double,
            @RequestParam(required = false, defaultValue = "0.0") z: Double,
            @RequestParam(required = false, defaultValue = "0.0") radius: Int
    ): MutableMap<String, ArrayList<System>> {
        return mutableMapOf(
                "systems" to service.edsmApi.findSystemsInSphere(system, x, y, z, radius)
        )
    }

    @RequestMapping("/findSystemsInCube")
    fun findSystemInCube(
            @RequestParam(required = false, defaultValue = "") system: String,
            @RequestParam(required = false, defaultValue = "0.0") x: Double,
            @RequestParam(required = false, defaultValue = "0.0") y: Double,
            @RequestParam(required = false, defaultValue = "0.0") z: Double,
            @RequestParam(required = false, defaultValue = "0.0") size: Int
    ): MutableMap<String, ArrayList<System>> {
        return mutableMapOf(
                "systems" to service.edsmApi.findSystemsInCube(system, x, y, z, size)
        )
    }
}