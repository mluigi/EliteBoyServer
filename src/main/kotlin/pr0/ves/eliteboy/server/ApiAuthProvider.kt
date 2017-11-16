package pr0.ves.eliteboy.server

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import pr0.ves.eliteboy.server.services.CommanderService
import java.math.BigInteger
import java.security.SecureRandom
import java.util.regex.Pattern
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class ApiAuthProvider : HandlerInterceptorAdapter() {
    @Autowired
    lateinit var service: CommanderService

    private val random: SecureRandom = SecureRandom()

    @Throws(Exception::class)
    override fun preHandle(request: HttpServletRequest?, response: HttpServletResponse?, handler: Any?): Boolean {
        val pattern = Pattern.compile("/api/cmdr/.+")
        val matcher = pattern.matcher(request!!.requestURI)

        var auth = false
        if (matcher.find()) {
            val token = service.commander.restApiToken

            auth = request.getHeader("X-EBOY-ACCESS-TOKEN") == token
        }
        if (!auth) {
            response!!.sendRedirect("/unauthorized")
        }
        return auth
    }

    private fun generateRandomString(): String {
        return BigInteger(130, random).toString(32)
    }

    fun generateAuthToken() {
        service.commander.restApiToken = generateRandomString()
    }

    fun generateRestPassword() {
        val password: String = this.generateRandomString()
        service.commander.restApiPassword = password
    }
}

