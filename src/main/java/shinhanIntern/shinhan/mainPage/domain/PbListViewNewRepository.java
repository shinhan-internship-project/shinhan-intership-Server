package shinhanIntern.shinhan.mainPage.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PbListViewNewRepository extends JpaRepository<PbListViewNew, Long> {
    List<PbListViewNew> findAllByCategory(String category);
}
