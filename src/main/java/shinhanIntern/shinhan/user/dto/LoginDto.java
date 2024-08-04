package shinhanIntern.shinhan.user.dto;

import lombok.*;

@Builder
@ToString
@Getter
@Setter
@AllArgsConstructor
public class LoginDto {
    private String email;
    private String password;
}
