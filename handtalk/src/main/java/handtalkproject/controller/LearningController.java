package handtalkproject.controller;

import handtalkproject.domain.entity.HandTalk;
import handtalkproject.service.LearningService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(value = "수어학습과 관련된 기능을 수행하는 컨트롤러")
@Slf4j
public class LearningController {

    private final LearningService learningService;

    public LearningController(LearningService learningService) {
        this.learningService = learningService;
    }

    @ApiOperation(value = "Day에 해당하는 수어 동작과 단어를 반환")
    @ApiImplicitParam(name = "day", value = "사용자가 선택한 day 값")
    @GetMapping("/learning/{day}")
    public List<HandTalk> getLearningData(@PathVariable int day) {
        return learningService.getLearningData(day);
    }
}
