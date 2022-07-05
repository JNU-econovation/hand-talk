package handtalkproject.domain.entity;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable = false)
    private Long id;
    private String email;
    private String password;
    private String nickname;
    private String profile;
    private boolean emailAuthorized;

    @OneToMany(mappedBy = "user")
    private List<LearningLog> learningLogs = new ArrayList<>();

    @Builder
    public User(String email, String password, String nickname, String profile, boolean emailAuthorized) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.profile = profile;
        this.emailAuthorized = emailAuthorized;
    }

    public User() {

    }

    public boolean canLogin(User user) {
        return email.equals(user.getEmail()) && password.equals(user.getPassword());
    }
}