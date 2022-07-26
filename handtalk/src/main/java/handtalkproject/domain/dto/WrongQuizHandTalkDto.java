package handtalkproject.domain.dto;

import handtalkproject.domain.entity.Day;
import handtalkproject.domain.entity.HandTalk;
import lombok.Builder;
import lombok.Data;

@Data
public class WrongQuizHandTalkDto {
    private int day;
    private String videoUrl;
    private String handtalkValue;

    public WrongQuizHandTalkDto() {
    }

    @Builder
    public WrongQuizHandTalkDto(int day, String videoUrl, String handtalkValue) {
        this.day = day;
        this.videoUrl = videoUrl;
        this.handtalkValue = handtalkValue;
    }

    public HandTalk toEntity() {
        return HandTalk.builder()
                .day(new Day(this.getDay()))
                .videoUrl(this.videoUrl)
                .handtalkValue(this.getHandtalkValue())
                .build();
    }
}
