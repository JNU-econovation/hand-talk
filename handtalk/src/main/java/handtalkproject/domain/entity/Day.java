package handtalkproject.domain.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Day {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "day_id", nullable = false)
    private Long id;

    @OneToMany(mappedBy = "day", cascade = CascadeType.ALL)
    private List<HandTalk> handTalks = new ArrayList<>();

    private int day;

    @OneToMany(mappedBy = "day")
    private List<LearningLog> learningLogs = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getDay() {
        return day;
    }
}
