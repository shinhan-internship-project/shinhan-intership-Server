package shinhanIntern.shinhan.chat.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import shinhanIntern.shinhan.chat.domain.ChatMessages;
import shinhanIntern.shinhan.chat.domain.ChatMessagesRepository;
import shinhanIntern.shinhan.chat.domain.ChatRooms;
import shinhanIntern.shinhan.chat.domain.ChatRoomsRepository;
import shinhanIntern.shinhan.chat.dto.ChatListDto;
import shinhanIntern.shinhan.chat.dto.ChatListForm;

@Service
@AllArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatRoomsRepository chatRoomsRepository;
    private final ChatMessagesRepository chatMessagesRepository;

    @Override
    public List<ChatListDto> getChatRooms(ChatListForm chatListForm) {
        if (chatListForm.getMyRole() == 0) {            // PB 일때
            List<ChatRooms> pbChatRooms = chatRoomsRepository.findAllByPbId(chatListForm.getMyId());

            return pbChatRooms.stream()
                .map(chatRooms -> new ChatListDto(
                    chatRooms.getId(),
                    chatRooms.getPbId(),
                    chatRooms.getCustomerId(),
                    chatRooms.getPbUncheckedCnt(),
                    chatRooms.getLastMessage()
                ))
                .collect(Collectors.toList());

        } else if (chatListForm.getMyRole() == 1) {     // 고객 일때
            List<ChatRooms> customerChatRooms = chatRoomsRepository.findAllByCustomerId(chatListForm.getMyId());

            return customerChatRooms.stream()
                .map(chatRooms -> new ChatListDto(
                    chatRooms.getId(),
                    chatRooms.getPbId(),
                    chatRooms.getCustomerId(),
                    chatRooms.getPbUncheckedCnt(),
                    chatRooms.getLastMessage()
                ))
                .collect(Collectors.toList());
        }
        throw new NullPointerException("올바르지 않은 role 입니다.");
    }

    @Override
    public List<ChatMessages> getMessages(String roomId) {
        List<ChatMessages> messages = chatMessagesRepository.findAllByChatRoomId(roomId);
        if (messages.isEmpty()) {
            return null;
        }
        return messages;
    }
}
