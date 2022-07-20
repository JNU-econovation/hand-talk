package handtalkproject.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class LearningHandTalkDto {

    private String videoUrl;

    private String handtalkValue;

    @Builder
    public LearningHandTalkDto(String videoUrl, String handtalkValue) {
        this.videoUrl = videoUrl;
        this.handtalkValue = handtalkValue;
    }
}
