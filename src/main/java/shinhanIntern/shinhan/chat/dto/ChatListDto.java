package shinhanIntern.shinhan.chat.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class ChatListDto {
    @NonNull
    private String chatRoomCode;
    @NonNull
    private Long myId;
    @NonNull
    private Long partnerId;
    @NonNull
    private int unCheckedMessageCount;
//    @NonNull //lastMessage 없을 시 에러나서 주석처리
    private String lastMessage;

    private String partnerName;

    private String partnerCategory;

}
