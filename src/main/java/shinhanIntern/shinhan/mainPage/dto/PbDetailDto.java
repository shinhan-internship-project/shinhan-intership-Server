package shinhanIntern.shinhan.mainPage.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import shinhanIntern.shinhan.mainPage.domain.Awards;
import shinhanIntern.shinhan.mainPage.domain.Portpolios;
import shinhanIntern.shinhan.user.domain.Users;

@Builder
@ToString
@Getter
@Setter
@JsonSerialize
@JsonDeserialize
@AllArgsConstructor
public class PbDetailDto {
    private Users pbUser;
    private List<Portpolios> portpolios;
    private List<Awards> awards;

}
