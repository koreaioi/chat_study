package com.test.chat.controller;

import com.test.chat.dto.MessageDto;
import com.test.chat.entity.PublishMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MessageController {

    private final RedisTemplate redisTemplate;

    private final ChannelTopic topic;

    private final RedisMessageListenerContainer redisMessageListender;

    @MessageMapping("/chats/messages/{room-id}")
    public void message(@DestinationVariable("room-id") Long roomId, MessageDto messageDto) {
        PublishMessage publishMessage =
                new PublishMessage(messageDto.getRoomId(), messageDto.getSenderId(), messageDto.getContent(), LocalDateTime.now());

        ChannelTopic topic = new ChannelTopic("/sub/chats/" + roomId);

        redisTemplate.convertAndSend(topic.getTopic(), publishMessage);
        log.info("Redis 서버에 메시지 전송 완료");
    }

}
