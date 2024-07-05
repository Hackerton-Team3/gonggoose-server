package konkuk.gonggoose.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
