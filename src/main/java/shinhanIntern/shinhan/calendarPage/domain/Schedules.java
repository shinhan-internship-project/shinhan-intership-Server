package shinhanIntern.shinhan.calendarPage.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import shinhanIntern.shinhan.calendarPage.dto.SchedulesDto;
import shinhanIntern.shinhan.user.dto.UsersDto;

import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Builder
public class Schedules {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false)
    private Long pbId;

    @Column
    private Long customId;

    @NonNull
    @Column(nullable = false)
    private OffsetDateTime dayTime;
    @NonNull
    @Column(nullable = false)
    private String scheduleName;

    @Column
    private String scheduleDescription;
    @Column
    private String schedulePlace;
    public SchedulesDto toDto() {
        return new SchedulesDto(dayTime, scheduleName, scheduleDescription,schedulePlace,customId, pbId);
    }

}
