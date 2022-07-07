package handtalkproject.service;

import handtalkproject.domain.entity.HandTalk;
import handtalkproject.repository.HandTalkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizService {
    private static final int FIRST_INDEX = 0;

    private final HandTalkRepository handTalkRepository;

    public HandTalk showMultipleChoice(int day) {
        List<HandTalk> datas = handTalkRepository.findAll()
                                                   .stream()
                                                   .filter(h -> h.isDay(day))
                                                   .collect(Collectors.toList());
        Collections.shuffle(datas);
        return datas.get(FIRST_INDEX);
    }
}
