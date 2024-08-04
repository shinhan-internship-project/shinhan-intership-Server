package shinhanIntern.shinhan.user.dto;

import lombok.*;

@Builder
@ToString
@Getter
@Setter
@AllArgsConstructor
public class UsersDto {
    private Long id;
    private String email;
    private String name;
}
