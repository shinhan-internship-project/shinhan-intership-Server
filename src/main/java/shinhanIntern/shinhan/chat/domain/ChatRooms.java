package shinhanIntern.shinhan.chat.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Builder
public class ChatRooms {
    @Id
    private String id;

    @NonNull
    private Long pbId;

    @NonNull
    private Long customerId;

    @NonNull
    private int pbUncheckedCnt;

    @NonNull
    private int customerUncheckedCnt;

    @NonNull
    private String lastMessage;
}