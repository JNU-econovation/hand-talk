package handtalkproject.domain.dto;

import handtalkproject.domain.entity.HandTalk;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class QuizMultipleChoiceDto {
    private String videoUrl;
    private List<String> multipleChoices;


    /**
     * @param handTalk
     * 정답에 해당하는거 생성자로 받아와서 동영상 데이터를 저장하고, 그와 매칭되는 단어 뜻을 리스트의 0번 인덱스에 저장한다.
     */
    public QuizMultipleChoiceDto(HandTalk handTalk) {
        this.videoUrl = handTalk.getVideoUrl();
        this.multipleChoices = new ArrayList<>();

        multipleChoices.add(handTalk.getHandtalkValue());
    }

    /**
     * @param handtalkValue
     * 세 가지 선지를 틀린 답으로 줄 것이기 때문에, 나머지 선지로 줄 단어들은 public 메서드의 인자로 받아와 List에 추가한다.
     */
    public void addWrongMultipleChoice(String handtalkValue) {
        multipleChoices.add(handtalkValue);
    }
}
