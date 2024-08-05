package shinhanIntern.shinhan.mainPage.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@ToString
@Getter
@Setter
@JsonSerialize
@JsonDeserialize
@AllArgsConstructor
public class EnterRoomForm {
    private String roomId;
    private int role;
}
