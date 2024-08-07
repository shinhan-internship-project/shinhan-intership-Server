package shinhanIntern.shinhan.mainPage.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PbViewListRepository extends JpaRepository<PbListView, Long> {
    
}
