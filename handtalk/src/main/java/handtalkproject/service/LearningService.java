package handtalkproject.service;

import handtalkproject.domain.dto.LearningHandTalkDto;
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

    public List<LearningHandTalkDto> getLearningData(int day) {
        return handTalkRepository.findAll()
                                 .stream()
                                 .filter(handTalk -> handTalk.isDay(day))
                                 .map(HandTalk::toLearningHandTalkDto)
                                 .collect(Collectors.toCollection(ArrayList::new));
    }
}
