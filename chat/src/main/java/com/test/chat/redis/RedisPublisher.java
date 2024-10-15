package com.test.chat.redis;

import com.test.chat.entity.PublishMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisPublisher {
    //RedisConfig에서 설정한 RedisTemplate과 타입을 맞춰야한다.
    private final RedisTemplate<String, Object> redisTemplate;
//    private final RedisTemplate<String, PublishMessage> redisTemplate; -> 주입이 안됨

    public void publish(ChannelTopic topic, Object obj){
        PublishMessage publishMessage = (PublishMessage) obj;
        redisTemplate.convertAndSend(topic.getTopic(), publishMessage);
    }

}
