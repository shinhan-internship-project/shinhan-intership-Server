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
public class DayReqForm {
    @NotNull(message = "input id")
    private Long id;
    @NotNull(message = "input today")
    private LocalDate today;
    @NotNull(message = "input role")
    private int role;
}
