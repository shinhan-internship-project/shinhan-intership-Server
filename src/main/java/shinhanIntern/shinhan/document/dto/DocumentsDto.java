package shinhanIntern.shinhan.document.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
@Builder
@ToString
@Getter
@Setter
@JsonSerialize
@JsonDeserialize
@AllArgsConstructor
public class DocumentsDto {
    private Long id;
    private String name;
    private String content;
    private LocalDateTime reservationDate;
}
