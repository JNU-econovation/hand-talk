package handtalkproject.service;

import handtalkproject.domain.dto.LearningHandTalkDto;
import handtalkproject.domain.entity.Day;
import handtalkproject.domain.entity.HandTalk;
import handtalkproject.repository.HandTalkRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
class LearningServiceTest {
    @Autowired
    HandTalkRepository handTalkRepository;

    @Autowired
    LearningService learningService;

    @Test
    @DisplayName("day에 해당하는 수어들을 조회했을 떄 잘 찾아와지는지 테스트")
    void getLearningData() {
        //given
        Day day = new Day(1);
        HandTalk handtalk = HandTalk.builder()
                                 .day(day)
                                 .build();

        Day day2 = new Day(1);
        HandTalk handtalk2 = HandTalk.builder()
                                    .day(day2)
                                    .build();

        handTalkRepository.save(handtalk);
        handTalkRepository.save(handtalk2);

        //when
        List<LearningHandTalkDto> learningData = learningService.getLearningData(1);

        //then
        assertThat(learningData.size())
                  .isEqualTo(3);
    }
}