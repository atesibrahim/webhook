package com.ates.webhook.controller;

import com.ates.webhook.entity.Message;
import com.ates.webhook.service.MessageSenderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("messages")
public class WebhookInterceptorController {

    private final MessageSenderService senderService;

    @PostMapping(path = "/{userName}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> sendMessage(@PathVariable String userName, @RequestBody Message message) {
        try {
            senderService.sendMessage(userName, message);
            return ResponseEntity.ok().build();
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}