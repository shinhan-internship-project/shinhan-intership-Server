package shinhanIntern.shinhan.user.service;

import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import shinhanIntern.shinhan.user.domain.UserRepository;
import shinhanIntern.shinhan.user.domain.Users;
import shinhanIntern.shinhan.user.dto.LoginDto;
import shinhanIntern.shinhan.user.dto.UsersDto;
import shinhanIntern.shinhan.utils.jwt.JwtProvider;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    public Users findByEmail() {
        Users user = userRepository.findByEmail("test@naver.com")
            .orElseThrow(()-> new NullPointerException("User not found"));
        return user;
    }

    public String login(LoginDto loginDto) {
        String email = loginDto.getEmail();
        String rawPassword = loginDto.getPassword();

        Users tryEmail = userRepository.findByEmail(email)
                .orElseThrow(()-> new NullPointerException("회원가입이 필요합니다"));

        // 비밀번호 일치 여부 확인
        if(rawPassword.equals(tryEmail.getPassword())){
            // JWT 토큰 반환
            String jwtToken = jwtProvider.createToken(tryEmail);
            return jwtToken;
        }
        return "password error";
    }

    public UsersDto getUserInfoFromToken(String cleanedToken) {
        UsersDto getUserInfo = jwtProvider.getUser(cleanedToken);
        return getUserInfo;
    }

}
