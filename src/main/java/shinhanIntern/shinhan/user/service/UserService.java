package shinhanIntern.shinhan.user.service;

import shinhanIntern.shinhan.user.domain.Users;
import shinhanIntern.shinhan.user.dto.LoginDto;

public interface UserService {
    Users findByEmail ();

    String login(LoginDto loginDto);
}
