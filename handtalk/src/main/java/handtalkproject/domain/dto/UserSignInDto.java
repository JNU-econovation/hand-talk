package handtalkproject.domain.dto;

import handtalkproject.domain.entity.User;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class UserSignInDto {
    private String email;
    private String password;

    public UserSignInDto() {
    }

    @Builder
    public UserSignInDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User toEntity() {
        return User.builder()
                   .email(email)
                   .password(password)
                   .build();
    }
}
