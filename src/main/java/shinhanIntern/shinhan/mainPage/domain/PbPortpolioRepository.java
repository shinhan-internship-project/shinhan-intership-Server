package shinhanIntern.shinhan.mainPage.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PbPortpolioRepository extends JpaRepository<Portfolios, Long> {
    List<Portfolios> findAllByPbId(Long pbId);
}
