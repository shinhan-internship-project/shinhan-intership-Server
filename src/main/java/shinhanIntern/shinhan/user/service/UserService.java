package shinhanIntern.shinhan.user.service;

import shinhanIntern.shinhan.user.domain.Users;
import shinhanIntern.shinhan.user.dto.FindUserDto;
import shinhanIntern.shinhan.user.dto.LoginDto;
import shinhanIntern.shinhan.user.dto.SigninDto;
import shinhanIntern.shinhan.user.dto.UsersDto;

public interface UserService {

    Users findByEmail ();

    String login(LoginDto loginDto);

    FindUserDto getUserInfoFromToken(String cleanedToken);

    UsersDto signin(SigninDto signinDto);
}
