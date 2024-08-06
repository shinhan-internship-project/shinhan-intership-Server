package shinhanIntern.shinhan.user.service;

import shinhanIntern.shinhan.user.domain.Users;
import shinhanIntern.shinhan.user.dto.*;

public interface UserService {

    Users findByEmail ();

    String login(LoginDto loginDto);

    FindUserDto getUserInfoFromToken(String cleanedToken);

    UsersDto signin(SigninDto signinDto);

    SearchUserDto searchUser(Long id);
}
