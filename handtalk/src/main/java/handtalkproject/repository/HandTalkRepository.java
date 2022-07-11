package handtalkproject.repository;

import handtalkproject.domain.entity.HandTalk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HandTalkRepository extends JpaRepository<HandTalk, Long> {
}
