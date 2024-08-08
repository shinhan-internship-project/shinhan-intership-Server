package shinhanIntern.shinhan.user.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import shinhanIntern.shinhan.user.domain.UserRepository;
import shinhanIntern.shinhan.user.domain.Users;
import shinhanIntern.shinhan.user.dto.*;
import shinhanIntern.shinhan.utils.jwt.JwtProvider;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    public String findByEmail(String testEmail) {
        boolean canUser = userRepository.findByEmail(testEmail).isPresent();

        if(!canUser)
            return "사용가능한 이메일입니다.";
        else
            return "중복된 이메일입니다.";
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
                .photo(signinDto.getPhoto())
                .build();
        userRepository.save(newUser);
        return newUser.toDto();
    }

    @Override
    public SearchUserDto searchUser(Long id) {
        Users foundUser = userRepository.findById(id)
                .orElseThrow(()-> new NullPointerException("User not found"));

        SearchUserDto getUser = new SearchUserDto(foundUser.getName(),foundUser.getPhoto(), foundUser.getCategory());
        return getUser;
    }
}
