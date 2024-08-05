package shinhanIntern.shinhan.user.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import shinhanIntern.shinhan.user.domain.UserRepository;
import shinhanIntern.shinhan.user.domain.Users;
import shinhanIntern.shinhan.user.dto.FindUserDto;
import shinhanIntern.shinhan.user.dto.LoginDto;
import shinhanIntern.shinhan.user.dto.SigninDto;
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

    public FindUserDto getUserInfoFromToken(String cleanedToken) {
        FindUserDto getUserInfo = jwtProvider.getUser(cleanedToken);
        return getUserInfo;
    }

    public UsersDto signin(SigninDto signinDto) {
        // 이메일 중복 확인
        if (userRepository.findByEmail(signinDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 계정입니다.");
        }

        Users newUser = Users.builder()
                .name(signinDto.getName())
                .email(signinDto.getEmail())
                .password(signinDto.getPassword())
                .cash(signinDto.getCash())
                .role(1)
                .build();
        userRepository.save(newUser);
        return newUser.toDto();
    }
}
