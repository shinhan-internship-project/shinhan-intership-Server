package shinhanIntern.shinhan.calendarPage.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Builder
@ToString
@Getter
@Setter
@JsonSerialize
@JsonDeserialize
@AllArgsConstructor
public class CalendarReqForm {
    @NotNull(message = "input id")
    private Long id;
    @NotNull(message = "input startDate")
    private int year;
    @NotNull(message = "input startDate")
    private int month;
    @NotNull(message = "input role")
    private int role;
}
