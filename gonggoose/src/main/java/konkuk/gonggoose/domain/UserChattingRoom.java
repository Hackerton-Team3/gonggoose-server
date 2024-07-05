package konkuk.gonggoose.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class UserChattingRoom {
    private Long id;
    private Long userId;
    private Long chattingRoomId;
    private LocalDateTime create_at;
    private LocalDateTime update_at;

    public static UserChattingRoom create(Long userId, Long chattingRoomId){
        UserChattingRoom userChattingRoom = new UserChattingRoom();
        userChattingRoom.setUserId(userId);
        userChattingRoom.setChattingRoomId(chattingRoomId);
        return userChattingRoom;
    }
}
