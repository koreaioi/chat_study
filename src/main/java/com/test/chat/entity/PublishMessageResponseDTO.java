package com.test.chat.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.io.Serial;
import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PublishMessageResponseDTO {

    @NotNull
    private Long roomId;
    @NotNull
    private Long senderId;
    @NotNull
    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createAt;

    public PublishMessageResponseDTO(PublishMessage publishMessage) {
        this.roomId = publishMessage.getRoomId();
        this.senderId = publishMessage.getSenderId();
        this.content = publishMessage.getContent();
        this.createAt = LocalDateTime.now();
    }


}
