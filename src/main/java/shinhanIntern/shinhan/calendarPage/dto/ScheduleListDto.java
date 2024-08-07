package shinhanIntern.shinhan.calendarPage.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@ToString
@Getter
@Setter
@JsonSerialize
@JsonDeserialize
@AllArgsConstructor
public class ScheduleListDto {
    private Long id;
    private LocalDateTime dayTime;
    private String scheduleName;
    private String scheduleDescription;
    private String schedulePlace;
    private Long customId;
    private Long pbId;
    private String partnerName;
}
