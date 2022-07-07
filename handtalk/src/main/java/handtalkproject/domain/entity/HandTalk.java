package handtalkproject.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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

    private String videoUrl;

    private String handtalkValue;

    public boolean isDay(int day) {
        return this.day.getDay() == day;
    }
}
