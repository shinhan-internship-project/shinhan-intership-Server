package shinhanIntern.shinhan.chat.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomsRepository extends JpaRepository<ChatRooms, Long> {

    List<ChatRooms> findAllByPbId(Long myId);

    List<ChatRooms> findAllByCustomerId(Long myId);
}
