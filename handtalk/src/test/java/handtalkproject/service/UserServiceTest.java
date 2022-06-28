package handtalkproject.service;

import handtalkproject.domain.dto.UserSignInDto;
import handtalkproject.domain.entity.User;
import handtalkproject.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class UserServiceTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    private User user;
    private User savedUser;

    @BeforeEach
    void setUp() {
        user = User.builder()
                   .email("email1")
                   .password("password1")
                   .nickname("name1")
                   .profile("profile1")
                   .emailAuthorized(false)
                   .build();

        savedUser = userRepository.save(user);
    }

    @Test
    @DisplayName("사용자 회원가입이 잘 되는지 테스트")
    void signUp() {
        //given
        //when
        //then
        assertThat(user).isEqualTo(savedUser);
    }

    @Test
    @DisplayName("이미 존재하는 이메일로 회원가입을 시도할 때 회원가입이 안되도록 하는지 테스트")
    void duplicatedEmailsignUp() {
        //given
        //when
        User duplicatedUser = User.builder()
                                  .email("email1")
                                  .password("password1")
                                  .nickname("name1")
                                  .profile("profile1")
                                  .emailAuthorized(false)
                                  .build();

        //then
        assertThatThrownBy(() -> userService.save(duplicatedUser)).isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("로그인이 잘 되는지 테스트")
    void login() {
        //given
        UserSignInDto userSignInDto = UserSignInDto.builder()
                                                   .email("email1")
                                                   .password("password1")
                                                   .build();

        User inputUser = userSignInDto.toEntity();

        //when
        User loginUser = userService.login(inputUser);

        //then
        assertThat(savedUser).isEqualTo(loginUser);
    }
}