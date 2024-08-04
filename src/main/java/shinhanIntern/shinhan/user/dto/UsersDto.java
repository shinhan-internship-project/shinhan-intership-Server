package shinhanIntern.shinhan.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@ToString
@Getter
@Setter
@AllArgsConstructor
public class UsersDto {
    @NotNull
    private Long id;
    @Email
    @NotBlank(message = "이메일이 없습니다.")
    private String email;
    @NotBlank(message = "이름이 없습니다.")
    private String name;
}
