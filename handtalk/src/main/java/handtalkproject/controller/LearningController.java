package handtalkproject.controller;

import handtalkproject.domain.dto.LearningHandTalkDto;
import handtalkproject.domain.entity.User;
import handtalkproject.exception.NoAuthenticationException;
import handtalkproject.service.LearningService;
import handtalkproject.utils.UserSessionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.xml.transform.Result;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(value = "수어학습과 관련된 기능을 수행하는 컨트롤러")
@Slf4j
public class LearningController {
    private static final String NO_AUTHENTICATION_MESSAGE = "로그인이 필요한 서비스 입니다.";

    private final LearningService learningService;
    private final HttpSession session;

    @ApiOperation(value = "Day에 해당하는 수어 동작과 단어를 반환")
    @ApiImplicitParam(name = "day", value = "사용자가 선택한 day 값")
    @GetMapping("/learning/{day}")
    public List<LearningHandTalkDto> getLearningData(@PathVariable int day) {
        return learningService.getLearningData(day);
    }

    public User getLoginedUser() {
        return (User) session.getAttribute(UserSessionUtils.USER_SESSION_KEY);
    }

    @ExceptionHandler(NoAuthenticationException.class)
    public String noAuth(NoAuthenticationException exception) {
        return exception.getMessage();
    }
}