package shinhanIntern.shinhan.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@ToString
@Getter
@Setter
@AllArgsConstructor
public class FindUserDto {
    private Long id;
    private int role;
}
