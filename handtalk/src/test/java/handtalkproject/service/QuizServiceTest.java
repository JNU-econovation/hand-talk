package handtalkproject.service;

import handtalkproject.domain.dto.QuizMultipleChoiceDto;
import handtalkproject.domain.entity.Day;
import handtalkproject.domain.entity.HandTalk;
import handtalkproject.domain.entity.User;
import handtalkproject.domain.entity.WrongQuizHandTalk;
import handtalkproject.repository.HandTalkRepository;
import handtalkproject.repository.WrongQuizHandTalkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class QuizServiceTest {

    @Autowired
    HandTalkRepository handTalkRepository;

    @Autowired
    WrongQuizHandTalkRepository wrongQuizHandTalkRepository;

    @Autowired
    QuizService quizService;

    User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                   .profile("profile")
                   .email("email")
                   .nickname("nickname")
                   .password("password")
                   .emailAuthorized(true)
                   .build();
    }



    @Test
    @DisplayName("Day에 해당하는 퀴즈 문제 출제 요청이 들어왔을 때 잘 동영상과 단어 값을 잘 반환하는지 테스트")
    void showQuizMultipleChoices() {
        //given
        HandTalk handTalk1 = generateDummyHandTalk();
        HandTalk handTalk2 = generateDummyHandTalk();
        HandTalk handTalk3 = generateDummyHandTalk();

        QuizMultipleChoiceDto quizMultipleChoiceDto = new QuizMultipleChoiceDto(handTalk1);
        quizMultipleChoiceDto.addWrongMulitipleChoice(handTalk2.getHandtalkValue());
        quizMultipleChoiceDto.addWrongMulitipleChoice(handTalk3.getHandtalkValue());

        handTalkRepository.save(handTalk1);
        handTalkRepository.save(handTalk2);
        handTalkRepository.save(handTalk3);

        //when
        QuizMultipleChoiceDto result = quizService.showQuizMultipleChoices(1);

        //then
        assertThat(result.getMultipleChoices()
                         .size()).isEqualTo(3);
    }

    @Test
    @DisplayName("특정 사용자에 오답을 저장하는 요청이 들어왔을 때 오답이 잘 저장되는지 테스트")
    void saveWrongQuizHandTalk() {
        //given
        HandTalk handTalk = generateDummyHandTalk();

        //when
        quizService.saveWrongQuizHandTalk(user, handTalk);

        //then
        WrongQuizHandTalk result = wrongQuizHandTalkRepository.findByHandTalk(handTalk)
                                                              .get();

        assertThat(result.getHandTalk()).isEqualTo(handTalk);
    }

    @Test
    @DisplayName("Day에 해당하는 오답을 보여달라는 요청이 들어왔을 때 Day에 해당하는 오답을 잘 반환하는지 테스트")
    void showAllWrongQuizHandtalks() {
        HandTalk handTalk = generateDummyHandTalk();

        //given
        WrongQuizHandTalk wrongQuizHandTalk = WrongQuizHandTalk.builder()
                                                               .user(user)
                                                               .handTalk(handTalk)
                                                               .build();
        wrongQuizHandTalkRepository.save(wrongQuizHandTalk);

        //when
        List<WrongQuizHandTalk> wrongQuizHandTalks = quizService.showAllWrongQuizHandtalks(user, 1);

        //then
        assertThat(wrongQuizHandTalks.get(0)
                                     .isSameUser(user)).isTrue();
    }
    HandTalk generateDummyHandTalk() {
        Day day = new Day(1);

        return HandTalk.builder()
                       .day(day)
                       .videoUrl("videoUrl")
                       .handtalkValue("handtalkValue")
                       .build();
    }
}