package shinhanIntern.shinhan.chat.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Builder
@ToString
@Getter
@Setter
@JsonSerialize
@JsonDeserialize
public class ChatCreateForm {
    @NonNull
    private Long myId;
    @NonNull
    private Long pbId;
    @NonNull
    private int myRole;
}
