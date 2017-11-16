package pr0.ves.eliteboy.server.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import pr0.ves.eliteboy.server.ApiAuthProvider

@Configuration
class WebConfig : WebMvcConfigurerAdapter() {

    @Autowired
    lateinit var authProvider: ApiAuthProvider

    override fun addCorsMappings(registry: CorsRegistry?) {
        registry!!.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST")
    }

    override fun addInterceptors(registry: InterceptorRegistry?) {
        registry!!.addInterceptor(authProvider)
                .addPathPatterns("/api/cmdr/**")
                .excludePathPatterns("/api/auth")
    }
}