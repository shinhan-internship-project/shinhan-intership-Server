package shinhanIntern.shinhan.mainPage.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PbViewListRepository extends JpaRepository<PbListView, Long> {

    List<PbListView> findAllByCategory(String category);
}
