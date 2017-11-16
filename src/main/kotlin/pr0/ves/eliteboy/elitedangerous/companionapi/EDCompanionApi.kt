package pr0.ves.eliteboy.elitedangerous.companionapi

import com.google.gson.GsonBuilder
import mu.KLogging
import pr0.ves.eliteboy.elitedangerous.companionapi.data.Commodity
import pr0.ves.eliteboy.elitedangerous.companionapi.data.Market
import pr0.ves.eliteboy.elitedangerous.companionapi.data.Profile
import pr0.ves.eliteboy.elitedangerous.companionapi.data.Shipyard
import pr0.ves.eliteboy.elitedangerous.companionapi.data.deserializers.CommodityDeserializer
import pr0.ves.eliteboy.server.Commander
import java.io.DataOutputStream
import java.net.HttpCookie
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.time.Clock
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter


class EDCompanionApi(var commander: Commander) {
    companion object : KLogging() {
        val BASE_URL = "https://companion.orerve.net"
        val ROOT_URL = "/"
        val LOGIN_URL = "/user/login"
        val CONFIRM_URL = "/user/confirm"
        val PROFILE_URL = "/profile"
        val MARKET_URL = "/market"
        val SHIPYARD_URL = "/shipyard"
    }

    var currentState = State.NEEDS_LOGIN

    enum class Endpoints(val url: String) {
        PROFILE("$BASE_URL$PROFILE_URL"),
        MARKET("$BASE_URL$MARKET_URL"),
        SHIPYARD("$BASE_URL$SHIPYARD_URL"),
    }

    enum class State {
        NEEDS_LOGIN,
        NEEDS_CONFIRMATION,
        READY
    }

    val gson = GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(Commodity::class.java, CommodityDeserializer())
            .create()!!

    fun login() {
        val connection = getRequest(BASE_URL + LOGIN_URL)
        connection.requestMethod = "POST"
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
        val email = URLEncoder.encode(commander.credentials!!.email, "UTF-8")
        val pass = URLEncoder.encode(commander.credentials!!.pwd(), "UTF-8")
        connection.doOutput = true
        DataOutputStream(connection.outputStream).writeBytes("email=$email&password=$pass")
        getResponse(connection)
        if (connection.responseCode == 302 && connection.getHeaderField("Location") == CONFIRM_URL) {
            currentState = State.NEEDS_CONFIRMATION
            logger.info { "Check email for confirmation code" }
        } else if (connection.responseCode == 302 && connection.getHeaderField("Location") == ROOT_URL) {
            currentState = State.READY
            logger.info { "Cookies still valid." }
        } else {
            logger.error { "Email or password incorrect." }
        }

    }

    fun confirm(code: String) {
        val urlConnection = getRequest(BASE_URL + CONFIRM_URL)
        urlConnection.requestMethod = "POST"
        urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
        val encCode = URLEncoder.encode(code, "UTF-8")
        urlConnection.doOutput = true
        DataOutputStream(urlConnection.outputStream).writeBytes("code=$encCode")
        getResponse(urlConnection)
        if (urlConnection.responseCode == 302 && urlConnection.getHeaderField("Location") == ROOT_URL) {
            currentState = State.READY
            logger.info { "Logged in." }
        }
        if (urlConnection.responseCode == 302 && urlConnection.getHeaderField("Location") == LOGIN_URL) {
            currentState = State.NEEDS_LOGIN
            logger.error { "Confirmation code incorrect" }
        }
    }

    private inline fun <reified T> get(endpoint: Endpoints): T {
        val url: String = endpoint.url
        val connection = getRequest(url)
        connection.doInput = true
        getResponse(connection)
        val json = getResponseData(connection)
        return gson.fromJson(json, T::class.java) as T
    }

    fun getProfile(): Profile {
        return get(Endpoints.PROFILE)
    }

    fun getMarket(): Market {
        return get(Endpoints.MARKET)
    }

    fun getShipyard(): Shipyard {
        return get(Endpoints.SHIPYARD)
    }

    private fun getRequest(url: String): HttpURLConnection {
        val request = URL(url).openConnection() as HttpURLConnection
        request.instanceFollowRedirects = false
        request.setRequestProperty("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 7_1_2 like Mac OS X) AppleWebKit/537.51.2 (KHTML, like Gecko) Mobile/11D257")
        if (commander.credentials!!.appId != null) {
            request.addRequestProperty("Cookie", getCompanionAppCookie(commander.credentials!!))
            request.addRequestProperty("Cookie", getMachineIdCookie(commander.credentials!!))
            request.addRequestProperty("Cookie", getMachineTokenCookie(commander.credentials!!))
        }
        return request
    }

    private fun getResponse(httpURLConnection: HttpURLConnection) {
        httpURLConnection.connect()
        updateCredentials(httpURLConnection)
        commander.toFile()
    }

    private fun getResponseData(connection: HttpURLConnection): String {
        return connection.inputStream.bufferedReader().readText()
    }

    private fun updateCredentials(connection: HttpURLConnection) {
        connection.headerFields["Set-Cookie"]?.forEach {
            val cookie = HttpCookie.parse(it)[0]
            when {
                cookie.name == "CompanionApp" -> commander.credentials!!.appId = cookie.value
                cookie.name == "mid" -> commander.credentials!!.machineId = cookie.value
                cookie.name == "mtk" -> commander.credentials!!.machineToken = cookie.value
            }
        }
    }


    private fun getCompanionAppCookie(credentials: Credentials): String {
        var appCookie = ""
        if (credentials.appId != null) {
            appCookie = "CompanionApp=${credentials.appId};Path=\"/\";Domain=\"companion.orerve.net\""
        }
        return appCookie
    }

    private fun getMachineIdCookie(credentials: Credentials): String {
        var machineIdCookie = ""
        if (credentials.machineId != null) {
            var date = if (credentials.machineId!!.indexOf("%7C") == -1) {
                LocalDateTime.now(Clock.systemUTC()).plusDays(7).format(DateTimeFormatter.RFC_1123_DATE_TIME)
            } else {
                val expiryseconds = credentials.machineId!!.substring(0, credentials.machineId!!.indexOf("%7C"))
                val expiryDateTime = LocalDateTime.of(1970, 1, 1, 0, 0, 0, 0).atOffset(ZoneOffset.UTC).plusSeconds(expiryseconds.toLong())
                expiryDateTime.format(DateTimeFormatter.RFC_1123_DATE_TIME)
            }
            date = date.removeSuffix("GMT") + "UTC"
            machineIdCookie = "mid=${credentials.machineId}; domain=\"companion.orerve.net\"; path=\"/\"; expires=$date; secure"
        }
        return machineIdCookie
    }

    private fun getMachineTokenCookie(credentials: Credentials): String {
        var machineTokenCookie = ""
        if (credentials.machineToken != null) {
            var date = if (credentials.machineToken!!.indexOf("%7C") == -1) {
                LocalDateTime.now(Clock.systemUTC()).plusDays(7).format(DateTimeFormatter.RFC_1123_DATE_TIME)
            } else {
                val expiryseconds = credentials.machineToken!!.substring(0, credentials.machineToken!!.indexOf("%7C"))
                val expiryDateTime = LocalDateTime.of(1970, 1, 1, 0, 0, 0, 0).atOffset(ZoneOffset.UTC).plusSeconds(expiryseconds.toLong())
                expiryDateTime.format(DateTimeFormatter.RFC_1123_DATE_TIME)
            }
            date = date.removeSuffix("GMT") + "UTC"
            machineTokenCookie = "mtk=${credentials.machineToken}; domain=\"companion.orerve.net\"; path=\"/\"; expires=$date; secure"
        }
        return machineTokenCookie
    }
}