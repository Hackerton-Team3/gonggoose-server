package konkuk.gonggoose.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import konkuk.gonggoose.domain.ChattingMessage;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class ChattingMessageResponse {
    private Long chattingMessageId;
    private Long chattingRoomId;
    private Long userId;
    private String nickname;
    private String content;
    private LocalDateTime createAt;

    public static ChattingMessageResponse create(Long userId, String nickname, ChattingMessage chattingMessage){
        return ChattingMessageResponse.builder()
                .chattingMessageId(chattingMessage.getId())
                .chattingRoomId(chattingMessage.getChattingRoomId())
                .userId(userId)
                .nickname(nickname)
                .content(chattingMessage.getContent())
                .createAt(chattingMessage.getCreateAt())
                .build();
    }
}
