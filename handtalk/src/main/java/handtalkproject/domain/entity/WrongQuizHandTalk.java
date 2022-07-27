package handtalkproject.domain.entity;

import handtalkproject.domain.dto.WrongQuizHandTalkDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class WrongQuizHandTalk {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "wrongQuizHandTalk_id", nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "handtalk_id")
    private HandTalk handTalk;

    public WrongQuizHandTalk() {
    }

    @Builder
    public WrongQuizHandTalk(User user, HandTalk handTalk) {
        this.user = user;
        this.handTalk = handTalk;
    }

    public boolean isSameUser(User user) {
        return this.user.getId() == user.getId();
    }

    public static WrongQuizHandTalkDto toDto(WrongQuizHandTalk wrongQuizHandTalk) {
        return WrongQuizHandTalkDto.builder()
                            .day(wrongQuizHandTalk.getHandTalk()
                                                  .getDay()
                                                  .getDay())
                            .videoUrl(wrongQuizHandTalk.getHandTalk()
                                                       .getVideoUrl())
                            .handtalkValue(wrongQuizHandTalk.getHandTalk()
                                                            .getHandtalkValue())
                            .build();
    }
}
