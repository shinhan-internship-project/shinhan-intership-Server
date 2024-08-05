package shinhanIntern.shinhan.chat;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shinhanIntern.shinhan.chat.domain.ChatMessages;
import shinhanIntern.shinhan.chat.domain.ChatRooms;
import shinhanIntern.shinhan.chat.dto.ChatListDto;
import shinhanIntern.shinhan.chat.dto.ChatListForm;
import shinhanIntern.shinhan.chat.service.ChatService;
import shinhanIntern.shinhan.utils.ApiUtils;
import shinhanIntern.shinhan.utils.ApiUtils.ApiResult;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatRestController {

    private final SimpMessageSendingOperations template;
    private final ChatService chatService;

    @PostMapping("/list")
    public ApiResult<List<ChatListDto>> getChatRoomList(@Valid @RequestBody ChatListForm chatListForm){
        try{
            List<ChatListDto> chatRoomsList = chatService.getChatRooms(chatListForm);
            return ApiUtils.success(chatRoomsList);
        }catch(NullPointerException e){
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/room/{roomId}")  // 최초에 채팅 리스트 가져오기
    public ApiResult<List<ChatMessages>> getChatMessages(@PathVariable String roomId){
        try{
            List<ChatMessages> messages = chatService.getMessages(roomId);
            return ApiUtils.success(messages);
        }catch(NullPointerException e){
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
