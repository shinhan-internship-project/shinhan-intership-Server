package shinhanIntern.shinhan.user.dto;

import lombok.*;

@Builder
@ToString
@Getter
@Setter
@AllArgsConstructor
public class SearchUserDto {
    private String name;
    private String photo;
    private String category;
}
