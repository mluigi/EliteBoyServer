package pr0.ves.eliteboy.server.controllers

import com.google.gson.JsonParser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import pr0.ves.eliteboy.elitedangerous.companionapi.EDCompanionApi
import pr0.ves.eliteboy.server.services.CommanderService

@RestController
@RequestMapping("/api/cmdr/edapi")
class EDCompanionApiController {
    @Autowired
    lateinit var service: CommanderService

    @RequestMapping("/login", method = arrayOf(RequestMethod.POST))
    fun loginEDApi(
            @RequestBody(required = false) json: String?
    ): MutableMap<String, String> {
        var email = ""
        var password = ""
        if (json != null) {
            val jsonObject = JsonParser().parse(json).asJsonObject
            email = jsonObject.get("email").asString ?: ""
            password = jsonObject.get("password").asString ?: ""
        }

        val triedLogin = service.loginEDApi(email, password)
        when (service.edApi!!.currentState) {
            EDCompanionApi.State.NEEDS_LOGIN -> {
                return if (triedLogin) {
                    mutableMapOf(
                            "message" to "Wrong email or password"
                    )
                } else {
                    mutableMapOf(
                            "message" to "Email and password needed"
                    )
                }
            }
            EDCompanionApi.State.NEEDS_CONFIRMATION -> {
                return mutableMapOf(
                        "message" to "Check email")
            }
            EDCompanionApi.State.READY -> {
                return mutableMapOf(
                        "message" to "Already logged in"
                )
            }
        }
    }

    @RequestMapping("/confirm")
    fun confirmEDApi(@RequestParam(required = true) code: String): MutableMap<String, String> {
        service.edApi!!.confirm(code)
        return when (service.edApi!!.currentState) {
            EDCompanionApi.State.NEEDS_LOGIN -> {
                mutableMapOf(
                        "message" to "Wrong email or password"
                )
            }
            EDCompanionApi.State.NEEDS_CONFIRMATION -> {
                service.edApi!!.currentState = EDCompanionApi.State.NEEDS_LOGIN
                mutableMapOf(
                        "message" to "Wrong email or password"
                )
            }
            EDCompanionApi.State.READY -> {
                mutableMapOf(
                        "message" to "Logged in successfully"
                )
            }
        }
    }


    @RequestMapping("/getProfile")
    fun getProfile(): MutableMap<String, Any> {
        return when (service.edApi!!.currentState) {
            EDCompanionApi.State.READY -> {
                mutableMapOf(
                        "message" to "Success",
                        "profile" to service.edApi!!.getProfile()
                )
            }
            else -> {
                mutableMapOf(
                        "message" to "Not logged in api"
                )
            }
        }
    }

    @RequestMapping("/getMarket")
    fun getMarket(): MutableMap<String, Any> {
        return when (service.edApi!!.currentState) {
            EDCompanionApi.State.READY -> {
                mutableMapOf(
                        "message" to "Success",
                        "market" to service.edApi!!.getMarket()
                )
            }
            else -> {
                mutableMapOf(
                        "message" to "Not logged in api"
                )
            }
        }
    }

    @RequestMapping("/getShipyard")
    fun getShipyard(): MutableMap<String, Any> {
        return when (service.edApi!!.currentState) {
            EDCompanionApi.State.READY -> {
                mutableMapOf(
                        "message" to "Success",
                        "shipyard" to service.edApi!!.getShipyard()
                )
            }
            else -> {
                mutableMapOf(
                        "message" to "Not logged in api"
                )
            }
        }
    }

}