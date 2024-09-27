package com.test.chat.controller;

import com.test.chat.Service.RoomService;
import com.test.chat.dto.ChatDto;
import com.test.chat.redis.RedisSubscriber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Controller
@Slf4j
@Validated
@RequestMapping("/chats")
@RequiredArgsConstructor
public class RoomController {
    private final RedisMessageListenerContainer redisMessageListener;
    private final RedisSubscriber redisSubscriber;
    private final RoomService roomService;

    // 방 생성 시 필요한 정보 (대화 할 상대방 ID)
    @PostMapping
    public ResponseEntity getOrCreateRoom(@RequestBody ChatDto.Post postDto) {

        // 회원 인증 과정 생략

        Long roomId = roomService.createRoom(postDto);

        ChannelTopic topic = new ChannelTopic("/sub/chats/" + roomId);
        redisMessageListener.addMessageListener(redisSubscriber, topic);

        URI location = UriComponentsBuilder.newInstance()
                .path("/chats/{room-id}")
                .buildAndExpand(roomId)
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
