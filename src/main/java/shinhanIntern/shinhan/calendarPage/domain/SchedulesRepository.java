package shinhanIntern.shinhan.calendarPage.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shinhanIntern.shinhan.document.domain.Documents;

import java.util.List;

@Repository
public interface SchedulesRepository extends JpaRepository<Schedules, Long> {
    List<Schedules> findAllByPbId(Long pbId);
    List<Schedules> findAllByCustomId(Long customId);

}
