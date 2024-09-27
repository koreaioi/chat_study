package com.test.chat.repository;


import com.test.chat.entity.ChatMessage;
import com.test.chat.entity.ChatRoom;
import com.test.chat.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<ChatRoom, Long> {

    Optional<ChatRoom> findBySenderAndReceiver(Member sender, Member receiver);

    Optional<ChatRoom> findBySenderIdAndReceiverId(Long senderId, Long receiverId);

}
