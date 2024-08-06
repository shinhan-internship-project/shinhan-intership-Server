package shinhanIntern.shinhan.document.dto;

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
public class SendDocumentForm {
    @NotNull(message = "input userId")
    private Long userId;
    @NotNull(message = "input pbId")
    private Long pbId;
    @NotBlank(message = "input content")
    private String content;
    @NotNull(message = "input date")
    private LocalDate date;
    @NotNull(message = "input time")
    private String time;
}
