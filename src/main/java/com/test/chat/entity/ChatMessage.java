package com.test.chat.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Document(collection = "chatMessage")
@Getter
@Builder
public class ChatMessage {

    @Id
    private String id; // MongoDb에서 사용하는 ObjectId

    private Long roomId;

    private Long senderId;

    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    public static ChatMessage of(PublishMessage publishMessage) {
        return ChatMessage.builder()
                .roomId(publishMessage.getRoomId())
                .senderId(publishMessage.getSenderId())
                .content(publishMessage.getContent())
                .createdAt(LocalDateTime.now())
                .build();
    }

}
