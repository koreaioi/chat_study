package com.test.chat.controller;

import com.test.chat.dto.MessageDto;
import com.test.chat.entity.ChatMessage;
import com.test.chat.entity.PublishMessage;
import com.test.chat.repository.chatmessage.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequiredArgsConstructor
public class MessageController {

    private final RedisTemplate redisTemplate;

    private final ChannelTopic topic;

    private final RedisMessageListenerContainer redisMessageListender;

    private final ChatMessageRepository chatMessageRepository;

    // 클라이언트는 /pub/chats/messages/ + roomId로 보낸다.
    @MessageMapping("/chats/messages/{room-id}")
    public void message(@DestinationVariable("room-id") Long roomId, MessageDto messageDto) {
        PublishMessage publishMessage =
                new PublishMessage(messageDto.getRoomId(), messageDto.getSenderId(), messageDto.getContent());

        // /sub/chats/ + roomId로 설정하고 Redis에 전송하면 메시지 리스너가 이를 받아서 처리한다.
        // 이때 메시지 리스너는 RedisSubscriber의 onMessage를 수행하게 된다.
        // 클라이언트가 MessageMapping을 하는 이유는 모든 토픽 구독자들에게 메시지를 보내기 위함이다.
        // 클라이언트의 /pub/chats/messages 요청을 받아서 (클라이언트 -> 서버)
        // 백엔드 MessageController에서는 /sub/chats으로 토픽을 구독자에게 메시지가 왓음을 알린다. (서버 -> 클라이언트)
        ChannelTopic topic = new ChannelTopic("/sub/chats/" + roomId);

        redisTemplate.convertAndSend(topic.getTopic(), publishMessage);
        log.info("Redis 서버에 메시지 전송 완료");

        // 보낸 메시지를 몽고 디비에 저장하는 로직 추가해야함.
        // 보낸 메시지를 채팅방의 마지막 메시지로 저장하는 로직 추가


        chatMessageRepository.save(ChatMessage.of(publishMessage));
        log.info("MongoDB Save ChatMessage ");
    }

}
