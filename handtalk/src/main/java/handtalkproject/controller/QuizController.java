package handtalkproject.controller;

import handtalkproject.domain.entity.HandTalk;
import handtalkproject.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quizService;

    @GetMapping("/quiz/{day}")
    public HandTalk showMultipleChoice(@PathVariable int day) {
        return quizService.showMultipleChoice(day);
    }
}
