package shinhanIntern.shinhan.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class SigninDto {
    @Email
    @NotBlank(message = "이메일이 없습니다.")
    private String email;
    @NotBlank(message = "이름이 없습니다.")
    private String name;
    @NotBlank(message = "비밀번호가 없습니다.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{1,15}$", message = "비밀번호는 1~15자 이내의 영어와 숫자의 조합이어야 합니다.")
    private String password;
    @NotNull(message = "자산정보가 없습니다.")
    private Long cash;

    private String photo;
}
