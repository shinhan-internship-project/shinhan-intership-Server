package shinhanIntern.shinhan.chat.service;

import java.util.List;
import shinhanIntern.shinhan.chat.domain.ChatMessages;
import shinhanIntern.shinhan.chat.domain.ChatRooms;
import shinhanIntern.shinhan.chat.domain.SendMessageForm;
import shinhanIntern.shinhan.chat.dto.ChatCreateForm;
import shinhanIntern.shinhan.chat.dto.ChatListDto;
import shinhanIntern.shinhan.chat.dto.ChatListForm;
import shinhanIntern.shinhan.mainPage.dto.EnterRoomForm;


public interface ChatService {
    List<ChatListDto> getChatRooms(ChatListForm chatListForm);

    List<ChatMessages> enterRoom(EnterRoomForm enterRoomForm);

    ChatMessages saveMessage(SendMessageForm sendMessageForm);

    void updateRoom(SendMessageForm sendMessageForm, int nowMember);

    ChatRooms createRoom(ChatCreateForm chatCreateForm);

    List<ChatRooms> findCustomerChatList(Long customerId);
    List<ChatRooms> findPbChatList(Long pbId);
}
