package org.example.chatws.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Configuración de WebSocket para habilitar mensajería en tiempo real.
 * Esta clase configura el broker de mensajes y los endpoints de conexión WebSocket.
 */
@Configuration
@EnableWebSocketMessageBroker 
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // Prefijo para los destinos de tipo topic (suscripciones)
    public static final String TOPIC = "/topic";
    // Prefijo para los destinos de la aplicación (mensajes enviados por clientes)
    public static final String APP = "/app";

    /**
     * Configura el broker de mensajes.
     * - enableSimpleBroker: Habilita un broker en memoria simple para mensajes de tipo topic y queue.
     *   Los clientes pueden suscribirse a destinos que comienzan con /topic (broadcast) y /queue (privado).
     * - setApplicationDestinationPrefixes: Define el prefijo para destinos de mensajes
     *   enviados desde el cliente al servidor (ej: /app/chat.sendMessage).
     * 
     * @param config Registro de configuración del broker de mensajes
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker(TOPIC, "/queue");
        config.setApplicationDestinationPrefixes(APP);
    }

    @Override 
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat").withSockJS();
    }
}
