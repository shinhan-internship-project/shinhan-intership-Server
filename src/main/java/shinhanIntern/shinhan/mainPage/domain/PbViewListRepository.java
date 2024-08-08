package shinhanIntern.shinhan.mainPage.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PbViewListRepository extends JpaRepository<PbListView, Long> {

    @Query("SELECT u FROM PbListView u WHERE u.name LIKE %:keyword%")
    List<PbListView> findAllByName(@Param("keyword")String keyword);

    List<PbListView> findAllByCategory(String category);
}
