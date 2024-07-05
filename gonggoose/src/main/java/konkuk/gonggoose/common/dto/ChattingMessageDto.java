package konkuk.gonggoose.common.dto;

import lombok.Getter;

import javax.validation.constraints.Size;

@Getter
public class ChattingMessageDto {
    @Size(max = 1000)
    private String content;
}
