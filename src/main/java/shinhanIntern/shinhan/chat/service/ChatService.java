package shinhanIntern.shinhan.chat.service;

import java.util.List;
import shinhanIntern.shinhan.chat.domain.ChatMessages;
import shinhanIntern.shinhan.chat.domain.ChatRooms;
import shinhanIntern.shinhan.chat.dto.ChatListDto;
import shinhanIntern.shinhan.chat.dto.ChatListForm;


public interface ChatService {
    List<ChatListDto> getChatRooms(ChatListForm chatListForm);

    List<ChatMessages> getMessages(String roomId);
}
