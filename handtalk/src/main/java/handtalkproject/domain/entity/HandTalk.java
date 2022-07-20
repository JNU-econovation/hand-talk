package handtalkproject.domain.entity;

import handtalkproject.domain.dto.LearningHandTalkDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class HandTalk {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "handtalk_id", nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "day_id")
    private Day day;

    @OneToMany(mappedBy = "handTalk")
    private List<WrongQuizHandTalk> wrongQuizHandTalk;

    private String videoUrl;

    private String handtalkValue;

    @Builder
    public HandTalk(Day day, List<WrongQuizHandTalk> wrongQuizHandTalk, String videoUrl, String handtalkValue) {
        this.day = day;
        this.wrongQuizHandTalk = wrongQuizHandTalk;
        this.videoUrl = videoUrl;
        this.handtalkValue = handtalkValue;
    }

    public HandTalk() {

    }

    public boolean isDay(int day) {
        return this.day.getDay() == day;
    }

    public LearningHandTalkDto toLearningHandTalkDto() {
        return LearningHandTalkDto.builder()
                                  .videoUrl(videoUrl)
                                  .handtalkValue(handtalkValue)
                                  .build();
    }
}
