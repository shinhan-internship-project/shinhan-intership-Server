package shinhanIntern.shinhan.calendarPage.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SchedulesRepository extends JpaRepository<Schedules, Long> {
    List<Schedules> findAllByPbId(Long pbId);
    List<Schedules> findAllByCustomId(Long customId);

    boolean existsByDayTime(LocalDateTime dayTime);

    List<Schedules> findByDayTimeBetweenAndPbId(LocalDateTime start, LocalDateTime end, Long pbId);
    Optional<Schedules> findByPbIdAndDayTime(Long pbId, LocalDateTime dayTime);

}
