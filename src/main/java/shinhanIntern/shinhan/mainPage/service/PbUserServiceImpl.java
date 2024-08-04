package shinhanIntern.shinhan.mainPage.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import shinhanIntern.shinhan.mainPage.domain.PbUserRepository;
import shinhanIntern.shinhan.mainPage.domain.Users;

@Service
@AllArgsConstructor
public class UserServiceImpl implements PbUserService {
    private final PbUserRepository pbUserRepository;

    public Users findByEmail() {
        Users user = pbUserRepository.findByEmail("test@naver.com")
            .orElseThrow(()-> new NullPointerException("User not found"));
        return user;
    }
}
