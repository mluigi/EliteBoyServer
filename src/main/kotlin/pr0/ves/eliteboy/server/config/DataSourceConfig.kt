package pr0.ves.eliteboy.server.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import javax.sql.DataSource

@Configuration
@EnableJpaRepositories("pr0.ves.eliteboy.server.repositories", considerNestedRepositories = true)
@EntityScan(basePackages = arrayOf("pr0.ves.eliteboy.elitedangerous.journal"), basePackageClasses = arrayOf(Jsr310JpaConverters::class))
class DataSourceConfig {

    @Bean
    fun primaryDataSource(): DataSource =
            DataSourceBuilder.create()
                    .driverClassName("org.sqlite.JDBC")
                    .url("jdbc:sqlite:${System.getProperty("user.dir")}/store/entries.db")
                    .build()
}
