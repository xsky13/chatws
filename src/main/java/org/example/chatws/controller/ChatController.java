package org.example.chatws.controller;

import org.example.chatws.entity.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller 
public class ChatController {
    @MessageMapping("/chat.sendMessage") 
    @SendTo("/topic/public")
    public ChatMessage enviarMensaje(ChatMessage mensaje) {
        System.out.print("Mensaje recibido");
        return mensaje;
    }

    @MessageMapping ("/chat.addUser")
    @SendTo ("/topic/public")
    public ChatMessage agregarUsuario(ChatMessage mensaje) {
        return mensaje;
    }
}
