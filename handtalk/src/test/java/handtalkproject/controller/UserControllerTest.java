package handtalkproject.controller;

import handtalkproject.domain.entity.User;
import handtalkproject.service.AwsS3Service;
import handtalkproject.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.FileInputStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @Mock
    UserService userService;

    @Mock
    AwsS3Service awsS3Service;

    @InjectMocks
    UserController userController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                                 .build();
    }

    @Test
    @DisplayName("사용자 회원가입이 잘 되는지 테스트")
    void create() throws Exception {
        User user = createUser();

        when(awsS3Service.uploadProfile(any())).thenReturn("testUrl");
        when(userService.save(any()))
                .thenReturn(user);

        MockMultipartFile image = new MockMultipartFile("files", "maenji.jpeg", "image/jpeg", new FileInputStream("/Users/chaesang-yeob/Desktop/hand-talk-be/handtalk/src/main/resources/maenji.png"));

        mockMvc.perform(multipart("/users/signup")
                                .file(image)
                                .param("email", user.getEmail())
                                .param("password", user.getPassword())
                                .param("nickname", user.getNickname())
                                .param("emailAuthorized", String.valueOf(user.isEmailAuthorized()))
               )
               .andDo(print())
               .andExpect(status().isOk());
    }

    User createUser() {
        return User.builder()
                   .email("saint6839@gmail.com")
                   .password("password")
                   .nickname("nickname")
                   .profile("profile")
                   .emailAuthorized(true)
                   .build();
    }

    @Test
    @DisplayName("사용자 로그인이 잘 되는지 테스트")
    void login() throws Exception {
        //given
        User user = createUser();
        when(userService.login(any())).thenReturn(user);

        //when
        //then
        mockMvc.perform(post("/users/login")
                                .param("email", user.getEmail())
                                .param("password", user.getPassword()))
               .andDo(print())
               .andExpect(status().isOk());
    }
}