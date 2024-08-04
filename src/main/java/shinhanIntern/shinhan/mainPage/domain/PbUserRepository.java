package shinhanIntern.shinhan.mainPage.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shinhanIntern.shinhan.user.domain.Users;

@Repository
public interface PbUserRepository extends JpaRepository<Users, Long> {
    public Optional<Users> findByEmail(String email);

    List<Users> findAllByRole(Integer role);
}
