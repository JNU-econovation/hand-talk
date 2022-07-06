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
    private boolean emailAuthorized;

    public UserSignUpDto() {
    }

    @Builder
    public UserSignUpDto(String email, String password, String nickname, boolean emailAuthorized) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.emailAuthorized = emailAuthorized;
    }

    public User toEntity(String imageUrl) {
        return User.builder()
                   .email(email)
                   .password(password)
                   .nickname(nickname)
                   .profile(imageUrl)
                   .emailAuthorized(true)
                   .build();
    }
}
