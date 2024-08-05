package shinhanIntern.shinhan.document.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentsRepository extends JpaRepository<Documents, Long> {
    Optional<Documents> findByPbId(Long pbId);

    List<Documents> findAllByPbId(Long pbId);
    List<Documents> findAllByUserId(Long userId);
}
