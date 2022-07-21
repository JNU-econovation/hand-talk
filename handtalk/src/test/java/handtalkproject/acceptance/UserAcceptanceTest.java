package handtalkproject.acceptance;

import com.amazonaws.util.IOUtils;
import handtalkproject.controller.UserController;
import handtalkproject.domain.dto.UserSignInDto;
import handtalkproject.domain.dto.UserSignUpDto;
import handtalkproject.domain.entity.User;
import handtalkproject.utils.UserSessionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.util.AssertionErrors.*;


@SpringBootTest
public class UserAcceptanceTest {

    @Autowired
    UserController userController;

    @Autowired
    HttpSession httpSession;

    @Test
    @DisplayName("실제 프로필 사진을 이용해 회원 가입을 진행했을때, 사용자 정보에 이미지를 포함한 사용자 정보들이 잘 저장되는지 테스트")
    void saveTest() throws IOException {
        //given
        UserSignUpDto userSignUpDto = UserSignUpDto.builder()
                                                   .email("pinkhg@gmail.com")
                                                   .password("password")
                                                   .nickname("김현지")
                                                   .emailAuthorized(true)
                                                   .build();
        File file = new File("../handtalk/src/main/resources/maenji.png");
        FileInputStream input = new FileInputStream(file);

        MultipartFile multipartFile = new MockMultipartFile("maenji",
                                                            file.getName(), "image/png", IOUtils.toByteArray(input));

        //when
        User user = userController.create(userSignUpDto, multipartFile);

        //then
        Assertions.assertAll(
                () -> assertEquals("이메일 검증", userSignUpDto.getEmail(), user.getEmail()),
                () -> assertEquals("패스워드 검증", userSignUpDto.getPassword(), user.getPassword()),
                () -> assertEquals("닉네임 검증", userSignUpDto.getNickname(), user.getNickname()),
                () -> assertTrue("이메일 인증 여부 검증", user.isEmailAuthorized()),
                () -> assertNotNull("프로필 이미지 여부 검증", user.getProfile())
        );
    }

    @Test
    @DisplayName("회원가입이 되어있는 유저 이메일과 패스워드로 로그인이 잘 되는지 테스트")
    void loginTest() {
        //given
        UserSignInDto userSignInDto = UserSignInDto.builder()
                                                   .email("saint6839@gmail.com")
                                                   .password("password")
                                                   .build();

        //when
        User user = userController.login(userSignInDto);

        //then
        assertThat(userSignInDto.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    @DisplayName("로그아웃이 잘 되는지 테스트")
    void logoutTest() {
        //given
        UserSignInDto userSignInDto = UserSignInDto.builder()
                                                   .email("saint6839@gmail.com")
                                                   .password("password")
                                                   .build();
        userController.login(userSignInDto);

        //when
        userController.logout();

        //then
        assertThat(httpSession.getAttribute(UserSessionUtils.USER_SESSION_KEY)).isNull();
    }
}
