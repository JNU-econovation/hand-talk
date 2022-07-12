package handtalkproject.repository;

import handtalkproject.domain.entity.HandTalk;
import handtalkproject.domain.entity.WrongQuizHandTalk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WrongQuizHandTalkRepository extends JpaRepository<WrongQuizHandTalk, Long> {
    Optional<WrongQuizHandTalk> findByHandTalk(HandTalk handTalk);
}
