package handtalkproject.domain.entity;

import javax.persistence.*;

@Entity
public class HandTalk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "handtalk_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "day_id")
    private Day day;

    public Long getId() {
        return id;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public boolean isDay(int day) {
        return this.day.getDay() == day;
    }
}
