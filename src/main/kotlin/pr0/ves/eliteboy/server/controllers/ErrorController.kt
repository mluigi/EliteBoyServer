package pr0.ves.eliteboy.server.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.http.HttpServletResponse

@Controller
class ErrorController {
    @RequestMapping("/unauthorized")
    fun unauthorized(response: HttpServletResponse?) {
        response!!.writer.write("Unauthorized. Go to \"/api/auth\".")
        response.status = 401
    }
}