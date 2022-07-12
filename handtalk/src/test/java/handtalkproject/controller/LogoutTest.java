package handtalkproject.controller;

import handtalkproject.domain.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.HttpSession;

@SpringBootTest
public class LogoutTest {

    @Autowired
    HttpSession session;

    @Test
    void logoutTest() {
        //given
        User user = User.builder()
                        .email("email")
                        .emailAuthorized(true)
                        .nickname("nickname")
                        .password("password")
                        .profile("profile")
                        .build();

        session.setAttribute("user", user);

        //when
        session.invalidate();

        //then
        Assertions.assertThat(session.getAttribute("user"))
                  .isNull();
    }
}
