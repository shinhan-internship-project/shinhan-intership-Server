package shinhanIntern.shinhan.user;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;
import shinhanIntern.shinhan.chat.domain.ChatRooms;
import shinhanIntern.shinhan.chat.dto.ChatListDto;
import shinhanIntern.shinhan.chat.dto.ChatListForm;
import shinhanIntern.shinhan.chat.service.ChatService;
import shinhanIntern.shinhan.user.domain.Users;
import shinhanIntern.shinhan.user.dto.*;
import shinhanIntern.shinhan.user.service.UserService;
import shinhanIntern.shinhan.utils.ApiUtils;
import shinhanIntern.shinhan.utils.ApiUtils.ApiResult;

@Slf4j
@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserRestController {
    private final UserService userService;
    private final ChatService chatService;
    private final SimpMessageSendingOperations template;

    @PostMapping("/checkEmail")
    public ApiResult<String> getUserTest(@RequestBody CheckEmailDto checkEmailDto) {
        try{
            String canUser = userService.findByEmail(checkEmailDto.getEmail());
            return ApiUtils.success(canUser);
        }catch (NullPointerException e){
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ApiResult<String> login(
            @Valid
            @RequestBody LoginDto loginDto
    ) {
        try{
            String loginUserToken = userService.login(loginDto);
            return ApiUtils.success(loginUserToken);
        }catch (NullPointerException e){
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/info")
    public ApiResult<FindUserDto> getUser(
            @RequestHeader("Authorization") String token
    ) {
        try{
            String cleanedToken = token.replace("Bearer ", "");
            FindUserDto userInfo = userService.getUserInfoFromToken(cleanedToken);

            List<ChatListDto> chatRoomsList = chatService.getChatRooms(new ChatListForm(userInfo.getId(), userInfo.getRole()));
            template.convertAndSend("/sub/user/"+userInfo.getId(), chatRoomsList);
            return ApiUtils.success(userInfo);
        }catch (NullPointerException e){
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/signin")
    public ApiResult<UsersDto> signin(
            @Valid
            @RequestBody SigninDto signinDto
    ) {
        try{
            UsersDto newUser = userService.signin(signinDto);
            return ApiUtils.success(newUser);
        }catch (NullPointerException e){
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ApiResult<SearchUserDto> searchUser(@PathVariable("id") Long id) {
        try{
            SearchUserDto userInfo = userService.searchUser(id);
            return ApiUtils.success(userInfo);
        }catch (NullPointerException e){
            return ApiUtils.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
