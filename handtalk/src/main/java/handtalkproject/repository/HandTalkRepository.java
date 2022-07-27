package handtalkproject.repository;

import handtalkproject.domain.entity.HandTalk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HandTalkRepository extends JpaRepository<HandTalk, Long> {
    Optional<HandTalk> findByVideoUrl(String videoUrl);
}
