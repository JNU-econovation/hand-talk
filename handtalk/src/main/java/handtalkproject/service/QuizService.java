package handtalkproject.service;

import handtalkproject.domain.dto.QuizMultipleChoiceDto;
import handtalkproject.domain.dto.WrongQuizHandTalkDto;
import handtalkproject.domain.entity.HandTalk;
import handtalkproject.domain.entity.WrongQuizHandTalk;
import handtalkproject.repository.HandTalkRepository;
import handtalkproject.repository.WrongQuizHandTalkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizService {
    private static final int ANSWER_INDEX = 0;
    private static final int WRONG_INDEX= 1;
    private static final int WRONG_INDEX2 = 2;


    private final HandTalkRepository handTalkRepository;

    private final WrongQuizHandTalkRepository wrongQuizHandTalkRepository;

    public QuizMultipleChoiceDto showQuizMultipleChoices(int day) {
        List<HandTalk> datas = handTalkRepository.findAll()
                                                   .stream()
                                                   .filter(h -> h.isDay(day))
                                                   .collect(Collectors.toList());
        Collections.shuffle(datas);

        // 정답에 해당하는 동영상과 단어 뜻 0번 인덱스에 저장
        QuizMultipleChoiceDto quizMultiChoiceDto = new QuizMultipleChoiceDto(datas.get(ANSWER_INDEX));

        // 나머지 오답에 해당하는 객관식 선지에 들어갈 단어들을 QuizMultipleChoiceDto 객체에 저장
        for (int i = WRONG_INDEX; i <= WRONG_INDEX2; i++) {
            quizMultiChoiceDto.addWrongMultipleChoice(datas.get(i).getHandtalkValue());
        }

        return quizMultiChoiceDto;
    }

    public void saveWrongQuizHandTalk(HandTalk handTalk) {
        WrongQuizHandTalk wrongQuizHandTalk = WrongQuizHandTalk.builder()
//                                                               .user(loginedUser)
                                                               .handTalk(handTalk)
                                                               .build();
        wrongQuizHandTalkRepository.save(wrongQuizHandTalk);
    }

    public List<WrongQuizHandTalkDto> showAllWrongQuizHandtalks(int day) {
        return wrongQuizHandTalkRepository.findAll()
                                          .stream()
//                                                                     .filter(wrongQuizHandTalk -> wrongQuizHandTalk.isSameUser(loginedUser))
                                          .filter(wrongQuizHandTalk -> wrongQuizHandTalk.getHandTalk()
                                                                                        .isDay(day))
                                          .map(WrongQuizHandTalk::toDto)
                                          .collect(Collectors.toList());


    }
}
