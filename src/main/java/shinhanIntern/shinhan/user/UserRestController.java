package shinhanIntern.shinhan.user;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import shinhanIntern.shinhan.user.domain.Users;
import shinhanIntern.shinhan.user.dto.FindUserDto;
import shinhanIntern.shinhan.user.dto.LoginDto;
import shinhanIntern.shinhan.user.dto.SigninDto;
import shinhanIntern.shinhan.user.dto.UsersDto;
import shinhanIntern.shinhan.user.service.UserService;
import shinhanIntern.shinhan.utils.ApiUtils;
import shinhanIntern.shinhan.utils.ApiUtils.ApiResult;

@Slf4j
@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserRestController {
    private final UserService userService;

    @GetMapping("/test")
    public ApiResult<Users> getUserTest() {
        try{
            Users user = userService.findByEmail();
            return ApiUtils.success(user);
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
            // 토큰에서 "Bearer " 접두사를 제거 (일반적인 토큰 형식)
            String cleanedToken = token.replace("Bearer ", "");
            FindUserDto userInfo = userService.getUserInfoFromToken(cleanedToken);
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


}
