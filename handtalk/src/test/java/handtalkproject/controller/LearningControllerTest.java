package handtalkproject.controller;

import handtalkproject.domain.entity.Day;
import handtalkproject.domain.entity.HandTalk;
import handtalkproject.domain.entity.User;
import handtalkproject.service.LearningService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class LearningControllerTest {
    @Mock
    LearningService learningService;

    @Mock
    HttpSession session;

    @InjectMocks
    LearningController learningController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(learningController)
                                 .build();
    }

    @Test
    @DisplayName("day에 해당하는 수어를 호출하는 메서드가 잘 호출 되는지 테스트")
    void getLearningData() throws Exception {
        //given
        Day day = new Day(1);

        HandTalk handtalk = HandTalk.builder()
                                 .day(day)
                                 .build();

        List<HandTalk> handTalks = new ArrayList<>();
        handTalks.add(handtalk);

        User user = User.builder()
                         .profile("profile")
                         .email("email")
                         .nickname("nickname")
                         .password("password")
                         .emailAuthorized(true)
                         .build();

        when((User) session.getAttribute(any())).thenReturn(user);
        when(learningService.getLearningData(1)).thenReturn(handTalks);

        //when
        //then
        mockMvc.perform(get("/learning/{day}",day.getDay()))
               .andDo(print())
               .andExpect(status().isOk());
    }


}