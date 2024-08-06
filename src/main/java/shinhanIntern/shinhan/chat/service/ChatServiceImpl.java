package shinhanIntern.shinhan.chat.service;

import jakarta.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import shinhanIntern.shinhan.chat.domain.ChatMessages;
import shinhanIntern.shinhan.chat.domain.ChatMessagesRepository;
import shinhanIntern.shinhan.chat.domain.ChatRooms;
import shinhanIntern.shinhan.chat.domain.ChatRoomsRepository;
import shinhanIntern.shinhan.chat.domain.SendMessageForm;
import shinhanIntern.shinhan.chat.dto.ChatCreateForm;
import shinhanIntern.shinhan.chat.dto.ChatListDto;
import shinhanIntern.shinhan.chat.dto.ChatListForm;
import shinhanIntern.shinhan.mainPage.dto.EnterRoomForm;
import shinhanIntern.shinhan.user.domain.OfficeRepository;
import shinhanIntern.shinhan.user.domain.Offices;
import shinhanIntern.shinhan.user.domain.UserRepository;
import shinhanIntern.shinhan.user.domain.Users;

@Service
@Transactional
@AllArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatRoomsRepository chatRoomsRepository;
    private final ChatMessagesRepository chatMessagesRepository;
    private final UserRepository userRepository;
    private final OfficeRepository officeRepository;

    @Override
    public List<ChatListDto> getChatRooms(ChatListForm chatListForm) {
        if (chatListForm.getMyRole() == 0) {            // PB 일때

            List<ChatRooms> pbChatRooms = chatRoomsRepository.findAllByPbId(chatListForm.getMyId());

            return pbChatRooms.stream()
                .map(chatRooms -> {
                    Long partnerId = chatRooms.getCustomerId();
                    Users partner = userRepository.findById(partnerId)
                            .orElseThrow(() -> new NullPointerException("채팅 상대방이 존재하지 않습니다."));
                    Offices officeInfo = officeRepository.findById(partner.getOfficeId())
                        .orElseThrow(()-> new NullPointerException("회사 정보가 없어용"));
                    return ChatListDto.builder()
                            .chatRoomCode(chatRooms.getId())
                            .myId(chatRooms.getPbId())
                            .partnerId(partnerId)
                            .unCheckedMessageCount(chatRooms.getPbUncheckedCnt())
                            .lastMessage(chatRooms.getLastMessage())
                            .partnerName(partner.getName())
                            .partnerCategory("")
                            .officeName(officeInfo.getName())
                            .build();
                })
                .collect(Collectors.toList());

        } else if (chatListForm.getMyRole() == 1) {     // 고객 일때
            List<ChatRooms> customerChatRooms = chatRoomsRepository.findAllByCustomerId(chatListForm.getMyId());

            return customerChatRooms.stream()
                .map(chatRooms -> {
                    Long partnerId = chatRooms.getPbId();
                    Users partner = userRepository.findById(partnerId)
                            .orElseThrow(() -> new NullPointerException("채팅 상대방이 존재하지 않습니다."));
                    return ChatListDto.builder()
                            .chatRoomCode(chatRooms.getId())
                            .myId(chatRooms.getCustomerId())
                            .partnerId(partnerId)
                            .unCheckedMessageCount(chatRooms.getCustomerUncheckedCnt())
                            .lastMessage(chatRooms.getLastMessage())
                            .partnerName(partner.getName())
                            .partnerCategory(partner.getCategory())
                            .officeName("")
                            .build();
                })
                .collect(Collectors.toList());
        }
        throw new NullPointerException("올바르지 않은 role 입니다.");
    }

    @Override
    public List<ChatMessages> enterRoom(EnterRoomForm enterRoomForm) {
        ChatRooms room = chatRoomsRepository.findById(enterRoomForm.getRoomId())
            .orElseThrow(()-> new NullPointerException("해당 채팅방이 없습니다."));
        if(enterRoomForm.getRole() == 0){   // pb 가 방 들어가면 pb 가 안읽은거 초기화
            room.setPbUncheckedCnt(0);
        } else if (enterRoomForm.getRole() == 1) {  // 고객이 방 들어가면 고객이 안읽은거 초기화
            room.setCustomerUncheckedCnt(0);
        }
        List<ChatMessages> messages = chatMessagesRepository.findAllByChatRoomId(enterRoomForm.getRoomId());
        if (messages.isEmpty()) {
            return null;
        }
        return messages;
    }

    @Override
    public ChatMessages saveMessage(SendMessageForm sendMessageForm) {

        ChatMessages chatMessage = ChatMessages.builder()
            .chatRoomId(sendMessageForm.getRoomId())
            .message(sendMessageForm.getMessage())
            .senderId(sendMessageForm.getUserId())
            .isCheck(false)
            .sendTime(OffsetDateTime.now())
            .build();

        ChatMessages savedMessage = chatMessagesRepository.save(chatMessage);
        return savedMessage;
    }

    @Override
    public void updateRoom(SendMessageForm sendMessageForm, int nowMember) {
        // 채팅방 찾기
        ChatRooms room = chatRoomsRepository.findById(sendMessageForm.getRoomId())
            .orElseThrow(()->new NullPointerException("방이 없습니다."));
        // 현재 채팅방내에 몇명 있는지에 따라
        room.setLastMessage(sendMessageForm.getMessage());
        if(nowMember < 2){      // 한명 이하이면 상대방 안읽음에 +1
            if(sendMessageForm.getRole() == 0){
                room.setCustomerUncheckedCnt(room.getCustomerUncheckedCnt()+1);
            }else if (sendMessageForm.getRole() == 1) {
                room.setPbUncheckedCnt(room.getPbUncheckedCnt()+1);
            }
        }else if (nowMember == 2){      // 둘 다 있으면 상대방 안읽음 = 0
            if(sendMessageForm.getRole() == 0){
                room.setCustomerUncheckedCnt(0);
            }else if (sendMessageForm.getRole() == 1) {
                room.setPbUncheckedCnt(0);
            }
        }
    }

    @Override
    public ChatRooms createRoom(ChatCreateForm chatCreateForm) {

        String createRoomId = chatCreateForm.getPbId() + "chat" + chatCreateForm.getMyId();
        ChatRooms rooms = ChatRooms.builder()
            .id(createRoomId)
            .pbId(chatCreateForm.getPbId())
            .customerId(chatCreateForm.getMyId())
            .pbUncheckedCnt(0)
            .customerUncheckedCnt(0)
            .build();

        Optional<ChatRooms> foundRoom = chatRoomsRepository.findById(createRoomId);

        if (foundRoom.isPresent()) {
            throw new NullPointerException("이미 채팅방이 존재합니다.");
        }

        ChatRooms createdRoom = chatRoomsRepository.save(rooms);

        return createdRoom;
    }

    @Override
    public List<ChatRooms> findCustomerChatList(Long customerId) {
        return chatRoomsRepository.findAllByCustomerId(customerId);
    }
    @Override
    public List<ChatRooms> findPbChatList(Long pbId) {
        return chatRoomsRepository.findAllByPbId(pbId);
    }


}
