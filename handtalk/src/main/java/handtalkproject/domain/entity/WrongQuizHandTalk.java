package handtalkproject.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class WrongQuizHandTalk {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "wrongQuizHandTalk_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "handtalk_id")
    private HandTalk handTalk;

    public WrongQuizHandTalk() {
    }

    @Builder
    public WrongQuizHandTalk(User user, HandTalk handTalk) {
        this.user = user;
        this.handTalk = handTalk;
    }
}
