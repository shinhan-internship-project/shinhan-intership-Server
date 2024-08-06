package shinhanIntern.shinhan.chat;

import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shinhanIntern.shinhan.chat.domain.ChatMessages;
import shinhanIntern.shinhan.chat.domain.ChatRooms;
import shinhanIntern.shinhan.chat.domain.SendMessageForm;
import shinhanIntern.shinhan.chat.dto.ChatCreateForm;
import shinhanIntern.shinhan.chat.dto.ChatListDto;
import shinhanIntern.shinhan.chat.dto.ChatListForm;
import shinhanIntern.shinhan.chat.service.ChatService;
import shinhanIntern.shinhan.mainPage.dto.EnterRoomForm;
import shinhanIntern.shinhan.utils.ApiUtils;
import shinhanIntern.shinhan.utils.ApiUtils.ApiResult;

@Slf4j
@RestController
@RequestMapping("/api/chat")
@AllArgsConstructor
public class ChatRestController {

    private final SimpMessageSendingOperations template;
    private final ChatService chatService;
    private final WebSocketEventListener webSocketEventListener;

    @PostMapping("/list")   // 채팅방 리스트 조회
    public ApiResult<List<ChatListDto>> getChatRoomList(@Valid @RequestBody ChatListForm chatListForm){
        try{
            List<ChatListDto> chatRoomsList = chatService.getChatRooms(chatListForm);
            return ApiUtils.success(chatRoomsList);
        }catch(NullPointerException e){
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/createRoom")     // 상담하기 버튼
    public ApiResult<ChatRooms> createRoom(@Valid @RequestBody ChatCreateForm chatCreateForm){
        try{
            ChatRooms AllRooms = chatService.createRoom(chatCreateForm);
            return ApiUtils.success(AllRooms);
        }catch(NullPointerException e){
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/enterRoom")  // 채팅방 입장
    public ApiResult<List<ChatMessages>> getChatMessages(@RequestBody EnterRoomForm enterRoomForm){
        try{
            List<ChatMessages> messages = chatService.enterRoom(enterRoomForm);
            return ApiUtils.success(messages);
        }catch(NullPointerException e){
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @MessageMapping("/message")
    public ResponseEntity<Void> receiveMessage(@Valid @RequestBody SendMessageForm sendMessageForm){
        String roomId = sendMessageForm.getRoomId();
        try{
            // 1. 메세지 저장 후, 저장값 받아옴
            ChatMessages message = chatService.saveMessage(sendMessageForm);
            // 2. 방에 있는 유저의 수 확인하고, 읽음 안읽음 처리
            int nowMember = webSocketEventListener.getUserCount(roomId);
            chatService.updateRoom(sendMessageForm, nowMember);

            //  룸에 메세지 쏘기. 들어온 메세지
            template.convertAndSend("/sub/chat/"+roomId, message);
            return ResponseEntity.ok().build();

        }catch(NullPointerException e){
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
