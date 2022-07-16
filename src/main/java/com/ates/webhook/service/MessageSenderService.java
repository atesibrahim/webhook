package com.ates.webhook.service;

import com.ates.webhook.entity.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@PropertySource("classpath:application.properties")
@AllArgsConstructor
public class MessageSenderService {

    @Value("${hooks.url}")
    private String HOOKS_URL;

    @Value("${hooks.key}")
    private String key;

    @Value("${hooks.value}")
    private String value;

    public void sendMessage(String userName, Message message) throws JsonProcessingException {
        Map<String, String> USER_TO_CHANNEL_WEBHOOK =
                Map.of(key, value);

        String userChannelId = USER_TO_CHANNEL_WEBHOOK.get(userName);
        String userWebhookUrl = String.format(HOOKS_URL, userChannelId);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper objectMapper = new ObjectMapper();
        String messageJson = objectMapper.writeValueAsString(message);
        HttpEntity<String> entity = new HttpEntity<>(messageJson, headers);
        restTemplate.exchange(userWebhookUrl, HttpMethod.POST, entity, String.class);
    }
}
