package com.test.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class ChatDto {

    @Getter
    @AllArgsConstructor
    @RequiredArgsConstructor
    public static class Post{
        private long sender_id;
        private long receiver_id;
    }
}
