package handtalkproject.controller;

import handtalkproject.domain.dto.QuizMultipleChoiceDto;
import handtalkproject.domain.dto.WrongQuizHandTalkDto;
import handtalkproject.domain.entity.HandTalk;
import handtalkproject.domain.entity.User;
import handtalkproject.domain.entity.WrongQuizHandTalk;
import handtalkproject.exception.NoAuthenticationException;
import handtalkproject.service.QuizService;
import handtalkproject.utils.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class QuizController {
    private static final String NO_AUTHENTICATION_MESSAGE = "로그인이 필요한 서비스 입니다.";

    private final QuizService quizService;
    private final HttpSession session;

    @GetMapping("/quiz/{day}")
    public QuizMultipleChoiceDto showMultipleChoice(@PathVariable int day) {
        if(getLoginedUser() != null) {
            return quizService.showQuizMultipleChoices(day);
        }
        throw new NoAuthenticationException(NO_AUTHENTICATION_MESSAGE);
    }

    @PostMapping("/quiz/wrong")
    public void saveWrongQuizHandTalk(WrongQuizHandTalkDto wrongQuizHandTalkDto) {
        if(getLoginedUser() != null) {
            quizService.saveWrongQuizHandTalk(getLoginedUser(), wrongQuizHandTalkDto.toEntity());
        } else {
            throw new NoAuthenticationException(NO_AUTHENTICATION_MESSAGE);
        }
    }

    @GetMapping("/quiz/wrong/{day}")
    public List<WrongQuizHandTalk> showAllWrongQuizHandtalks(@PathVariable int day) {
        if(getLoginedUser() != null) {
            return quizService.showAllWrongQuizHandtalks(getLoginedUser(), day);
        }
        throw new NoAuthenticationException(NO_AUTHENTICATION_MESSAGE);
    }

    public User getLoginedUser() {
        return (User)session.getAttribute(UserSessionUtils.USER_SESSION_KEY);
    }
}
