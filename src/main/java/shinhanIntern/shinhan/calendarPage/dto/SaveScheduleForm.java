package shinhanIntern.shinhan.calendarPage.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
@Builder
@ToString
@Getter
@Setter
@JsonSerialize
@JsonDeserialize
@AllArgsConstructor
public class SaveScheduleForm {
    @NotNull(message = "input id")
    private Long id;
    @NotNull(message = "input role")
    private int role;
    @NotBlank(message = "input scheduleName")
    private String scheduleName;
    @NotNull(message = "input date")
    private LocalDate date;
    @NotNull(message = "input time")
    private LocalTime time;

    private String scheduleDescription;
    private String schedulePlace;
}
