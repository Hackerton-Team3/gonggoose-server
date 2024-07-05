package konkuk.gonggoose.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChattingMessage {
    private Long id;
    private Long userId;
    private Long chattingRoomId;
    private String content;
    private LocalDateTime createAt;

    public ChattingMessage(Long userId, Long chattingRoomId, String content) {
        this.userId = userId;
        this.chattingRoomId = chattingRoomId;
        this.content = content;
    }
}
