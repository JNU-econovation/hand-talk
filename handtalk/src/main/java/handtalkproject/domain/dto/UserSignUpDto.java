package handtalkproject.domain.dto;

import handtalkproject.domain.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignUpDto {
    private String email;
    private String password;
    private String nickname;
    private String profile;
    private boolean emailAuthorized;

    public UserSignUpDto() {
    }

    @Builder
    public UserSignUpDto(String email, String password, String nickname, String profile, boolean emailAuthorized) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.profile = profile;
        this.emailAuthorized = emailAuthorized;
    }

    public User toEntity() {
        return User.builder()
                   .email(email)
                   .password(password)
                   .nickname(nickname)
                   .profile(profile)
                   .emailAuthorized(true)
                   .build();
    }
}
