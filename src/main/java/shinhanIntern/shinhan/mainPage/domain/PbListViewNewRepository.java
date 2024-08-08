package shinhanIntern.shinhan.mainPage.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PbListViewNewRepository extends JpaRepository<PbListViewNew, Long> {
    List<PbListViewNew> findAllByCategory(String category);

    @Query("SELECT u FROM PbListViewNew u WHERE u.name LIKE %:keyword%")
    List<PbListViewNew> findAllByName(@Param("keyword")String keyword);

    List<PbListViewNew> findAllByInvestType(String investType);

    List<PbListViewNew> findAllByCategoryAndInvestType(String categoryString, String typeString);
}
