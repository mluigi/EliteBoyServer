package pr0.ves.eliteboy.server.controllers

import com.google.gson.JsonParser
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.web.bind.annotation.*
import pr0.ves.eliteboy.elitedangerous.companionapi.EDCompanionApi.State.*
import pr0.ves.eliteboy.elitedangerous.journal.JournalEntry
import pr0.ves.eliteboy.server.ApiAuthProvider
import pr0.ves.eliteboy.server.Settings
import pr0.ves.eliteboy.server.listeners.JournalWatcherListener
import pr0.ves.eliteboy.server.services.CommanderService
import javax.servlet.http.HttpServletResponse

@CrossOrigin
@RestController
class CommanderController : JournalWatcherListener {

    @Autowired
    lateinit var settings: Settings

    @Autowired
    lateinit var service: CommanderService

    @Autowired
    lateinit var authProvider: ApiAuthProvider

    @Autowired
    lateinit var template: SimpMessagingTemplate

    @RequestMapping("/api/auth", method = arrayOf(RequestMethod.POST))
    fun auth(
            @RequestBody json: String = "",
            response: HttpServletResponse
    ): MutableMap<String, String> {
        val jsonObject = JsonParser().parse(json).asJsonObject
        if (!jsonObject.has("name"))
            return mutableMapOf(
                    "error" to "Name required"
            )
        val name = jsonObject.get("name").asString
        val pass = jsonObject.get("password").asString

        return if (settings.commanders.contains(name)) {
            if (service.commander.name != name) {
                service.load(name)
            }

            if (service.commander.restApiPassword.isEmpty()) {
                authProvider.generateRestPassword()
                authProvider.generateAuthToken()
                service.start()
                mutableMapOf(
                        "message" to "Successfully started",
                        "restPassword" to service.commander.restApiPassword,
                        "token" to service.commander.restApiToken
                )
            } else {
                if (pass != service.commander.restApiPassword) {
                    response.status = HttpServletResponse.SC_UNAUTHORIZED
                    mutableMapOf("message" to "Unauthorized")
                } else {
                    authProvider.generateAuthToken()
                    service.start()
                    mutableMapOf(
                            "message" to "Successfully started",
                            "token" to service.commander.restApiToken)
                }
            }
        } else {
            mutableMapOf(
                    "message" to "No Commander found with that name"
            )
        }
    }


    @RequestMapping("/api/cmdr/getEntries")
    fun getEntries(
            @RequestParam(defaultValue = "0") page: Int
    ): MutableMap<String, Any> {
        val entries = service.db.entryRepo.findAll(PageRequest(page, 500)).content

        return mutableMapOf(
                "nextId" to page * 500 + entries.size,
                "entries" to entries
        )
    }


    override fun getEntries(entries: ArrayList<JournalEntry>) {
        entries.forEach {
            template.convertAndSend("/api/stomp/entries", it)
        }
    }

    companion object : KLogging()
}