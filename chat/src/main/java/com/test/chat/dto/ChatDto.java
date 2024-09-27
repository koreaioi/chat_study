package com.test.chat.dto;

import lombok.Getter;

public class ChatDto {

    @Getter
    public static class Post{
        private long sender_id;
        private long receiver_id;
    }
}
