package com.ates.webhook.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Message {
    private String text;
}
