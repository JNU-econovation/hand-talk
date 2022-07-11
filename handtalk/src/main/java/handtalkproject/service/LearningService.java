package handtalkproject.service;

import handtalkproject.domain.entity.HandTalk;
import handtalkproject.repository.HandTalkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class LearningService {
    private final HandTalkRepository handTalkRepository;

    public List<HandTalk> getLearningData(int day) {
        return handTalkRepository.findAll()
                          .stream()
                          .filter(handTalk -> handTalk.isDay(day))
                          .collect(Collectors.toCollection(ArrayList::new));
    }
}
