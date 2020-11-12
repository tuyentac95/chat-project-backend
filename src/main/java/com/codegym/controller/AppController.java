package com.codegym.controller;

import com.codegym.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
public class AppController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat/{to}")
    public void sendMessage(@DestinationVariable String to, Message message) {
        System.out.println("Handling send message: " + message + " to: " + to);
        simpMessagingTemplate.convertAndSend("/topic/messages/" + to, message);
    }

    @GetMapping("/registration/{username}")
    public ResponseEntity<Void> register(@PathVariable String username) {
        System.out.println("Register: " + username);
        return ResponseEntity.ok().build();
    }

    @MessageMapping("/notify/{group}")
    public void sendNotification(@DestinationVariable String group, Message message) {
        System.out.println("Handling notification " + message + " to group: " + group);
        simpMessagingTemplate.convertAndSend("/topic/notify/" + group, message);
    }
}
