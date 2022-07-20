package handtalkproject.controller;

import com.amazonaws.util.IOUtils;
import handtalkproject.domain.dto.UserSignInDto;
import handtalkproject.domain.dto.UserSignUpDto;
import handtalkproject.domain.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class UserAcceptanceTest {

    @Autowired
    UserController userController;

    @Test
    @DisplayName("실제 프로필 사진을 이용해 회원 가입을 진행했을때, 사용자 정보에 이미지를 포함한 사용자 정보들이 잘 저장되는지 테스트")
    void saveTest() throws IOException {
        //given
        UserSignUpDto userSignUpDto = UserSignUpDto.builder()
                                                   .email("hyeonji0718@gmail.com")
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
        assertThat(userSignUpDto.getEmail())
                  .isEqualTo(user.getEmail());
        assertThat(userSignUpDto.getPassword())
                  .isEqualTo(user.getPassword());
        assertThat(userSignUpDto.getNickname())
                  .isEqualTo(user.getNickname());
        assertThat(user.isEmailAuthorized())
                  .isTrue();
        assertThat(user.getProfile())
                  .isNotEmpty();
    }

    @Test
    @DisplayName("회원가입이 되어있는 유저 이메일과 패스워드로 로그인이 잘 되는지 테스트")
    void loginTest() {
        //given
        UserSignInDto userSignInDto = UserSignInDto.builder()
                                                   .email("hyeonji0718@gmail.com")
                                                   .password("password")
                                                   .build();

        //when
        User user = userController.login(userSignInDto);

        //then
        assertThat(userSignInDto.getEmail()).isEqualTo(user.getEmail());
    }
}
