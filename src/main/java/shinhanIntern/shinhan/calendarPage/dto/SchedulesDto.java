package shinhanIntern.shinhan.calendarPage.dto;

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
public class SchedulesDto {
    private LocalDateTime dayTime;
    private String scheduleName;
    private String scheduleDescription;
    private String schedulePlace;
    private Long customId;
    private Long pbId;
}
