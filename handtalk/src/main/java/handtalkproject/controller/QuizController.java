package handtalkproject.controller;

import handtalkproject.domain.dto.QuizMultipleChoiceDto;
import handtalkproject.domain.dto.WrongQuizHandTalkDto;
import handtalkproject.domain.entity.WrongQuizHandTalk;
import handtalkproject.exception.NoAuthenticationException;
import handtalkproject.service.QuizService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@Api(value = "수어퀴즈와 관련된 기능을 수행하는 컨트롤러")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class QuizController {
    private static final String NO_AUTHENTICATION_MESSAGE = "로그인이 필요한 서비스 입니다.";

    private final QuizService quizService;
//    private final HttpSession session;

    @ApiOperation(value = "수어->단어 퀴즈에서 Day에 따른 문제를 출제해 줌")
    @GetMapping("/quiz/hand-to-korean/{day}")
    public QuizMultipleChoiceDto showMultipleChoice(@PathVariable int day) {
//        if(getLoginedUser() != null) {
            return quizService.showQuizMultipleChoices(day);
//        }
//        throw new NoAuthenticationException(NO_AUTHENTICATION_MESSAGE);
    }

    @ApiOperation(value = "수어-단어 퀴즈에서 문제를 틀렸을 경우 오답노트에 저장 함")
    @PostMapping("/quiz/hand-to-korean/wrong")
    public void saveWrongQuizHandTalk(@RequestBody WrongQuizHandTalkDto wrongQuizHandTalkDto) {
//        if(getLoginedUser() != null) {
            quizService.saveWrongQuizHandTalk(wrongQuizHandTalkDto.toEntity());
//        } else {
//            throw new NoAuthenticationException(NO_AUTHENTICATION_MESSAGE);
//        }
    }

    @ApiOperation(value = "수어-단어 퀴즈에서 틀린 오답들을 day별로 보여줌")
    @GetMapping("/quiz/hand-to-korean/wrong/{day}")
    public List<WrongQuizHandTalkDto> showAllWrongQuizHandtalks(@PathVariable int day) {
        return quizService.showAllWrongQuizHandtalks(day);
    }

    @ExceptionHandler(NoAuthenticationException.class)
    public String noAuth(NoAuthenticationException exception) {
        return exception.getMessage();
    }
}
