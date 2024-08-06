package shinhanIntern.shinhan.calendarPage.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@JsonSerialize
@JsonDeserialize
@AllArgsConstructor
public class CheckTimeForm {
    @NotNull(message = "input pbId")
    private Long pbid;
    @NotNull(message = "input reservationDay")
    private LocalDate reservationDay;
}
