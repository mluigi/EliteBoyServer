package pr0.ves.eliteboy.server.config

import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry

@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig : AbstractWebSocketMessageBrokerConfigurer() {
    override fun registerStompEndpoints(registry: StompEndpointRegistry?) {
        registry!!.addEndpoint("/api/cmdr/listenForEntries").setAllowedOrigins("*")
        registry.addEndpoint("/api/cmdr/listenForEntries").setAllowedOrigins("*").withSockJS()
    }

    override fun configureMessageBroker(registry: MessageBrokerRegistry?) {
        registry!!.enableSimpleBroker("/api/stomp")
        registry.setApplicationDestinationPrefixes("/app")
    }
}