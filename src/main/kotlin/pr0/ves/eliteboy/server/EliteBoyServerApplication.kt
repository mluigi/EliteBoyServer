package pr0.ves.eliteboy.server

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackageClasses = arrayOf(EliteBoyServerApplication::class))
class EliteBoyServerApplication

fun main(args: Array<String>) {
    SpringApplication.run(EliteBoyServerApplication::class.java)
}