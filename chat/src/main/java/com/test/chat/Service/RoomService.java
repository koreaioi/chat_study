package com.test.chat.Service;

import com.test.chat.dto.ChatDto;
import com.test.chat.entity.ChatRoom;
import com.test.chat.repository.MemberRepository;
import com.test.chat.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.test.chat.entity.Member;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoomService {

    private final MemberRepository memberRepository;
    private final RoomRepository roomRepository;


    // 채팅방 생성
    public Long createRoom(ChatDto.Post postDto) {

        Long sender_id = postDto.getSender_id();
        Long receiver_id = postDto.getReceiver_id();

        Member sender = memberRepository.findById(sender_id).get();
        Member receiver = memberRepository.findById(receiver_id).get();

        ChatRoom chatRoom = ChatRoom.builder().sender(sender).receiver(receiver).build();

        ChatRoom saveChatRoom = roomRepository.save(chatRoom);

        return saveChatRoom.getRoomId();

//        // 반대일 수 있으니 두번 조회해야한다 ㅠ
//        Optional<ChatRoom> findChatRoom1 = roomRepository.findBySenderIdAndReceiverId(sender_id, receiver_id);
//        Optional<ChatRoom> findChatRoom2 = roomRepository.findBySenderIdAndReceiverId(receiver_id, sender_id);

    }

    // 상대방과 채팅방이 있으면 들어가기



//    public ChatRoom getAllRoom(Long roomId){
//
//
//    }

}
