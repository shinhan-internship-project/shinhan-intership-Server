package shinhanIntern.shinhan.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@ToString
@Getter
@Setter
@AllArgsConstructor
public class LoginDto {
    @Email
    @NotBlank(message = "이메일이 없습니다.")
    private String email;
    @NotBlank(message = "비밀번호가 없습니다.")
    private String password;
}
