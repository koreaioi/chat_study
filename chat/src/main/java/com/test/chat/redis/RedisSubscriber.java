package com.test.chat.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.chat.entity.PublishMessage;
import com.test.chat.entity.PublishMessageResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@Slf4j
@RequiredArgsConstructor
public class RedisSubscriber implements MessageListener{

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper obejctMapper;
    private final SimpMessageSendingOperations messageTemplate;


    // Subscriber와 메시지 전달 로직 메서드 명을 MessageListenerAdapter()의 매개인자에 추가 -> RedisConfig 파일에서 설정
//    @Override
//    public void onMessage(String message) throws JsonProcessingException {
//        log.info("publish 전 message: {}", message);
//        PublishMessage publishMessage = obejctMapper.readValue(message, PublishMessage.class);
//        // PublishMessage를 구독자(sub한 사람들)에게 메시지를 보낸다.
//        messageTemplate.convertAndSend("/sub/chats" + publishMessage.getRoomId(), publishMessage);
//        log.info("publish 후 message: {}", publishMessage.getContent());
//
//
//    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        // 역 직렬화
        String tmpMessage = redisTemplate.getStringSerializer().deserialize(message.getBody());

        log.info("publish 전 message: {}", tmpMessage);
        PublishMessage publishMessage = null;
        try {
//            obejctMapper.registerModule(new JavaTimeModule());
            publishMessage = obejctMapper.readValue(tmpMessage, PublishMessage.class);
            PublishMessageResponseDTO publishMessageResponseDTO = new PublishMessageResponseDTO(publishMessage);
            // roomId와 publishMessage를 같은 roomId 구독자들에게 보낸다.
            messageTemplate.convertAndSend("/sub/chats/" + publishMessage.getRoomId(), publishMessageResponseDTO);
            // PublishMessage를 구독자(sub한 사람들)에게 메시지를 보낸다.
            log.info("publish 후 message: {}", publishMessage.getContent());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

//    @Bean
//    public ObjectMapper objectMapper() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        // 자바 8 날짜/시간 모듈 추가
//        JavaTimeModule javaTimeModule = new JavaTimeModule();
//        // LocalDateTime 역직렬화에 대한 포맷 설정 (옵션)
//        javaTimeModule.addDeserializer(LocalDateTime.class,
//                new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//        objectMapper.registerModule(javaTimeModule);
//        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//
//        return objectMapper;
//    }
}
