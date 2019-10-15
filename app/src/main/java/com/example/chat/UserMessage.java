package com.example.chat;

import java.util.Date;

class UserMessage {
    String message;
    String name;
    long time;

    public UserMessage(String message, String name) {
        this.message = message;
        this.name = name;
    }
}
