package konkuk.gonggoose.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
public class ChattingRoomListResponse {
    private Long userId;
    private List<ChattingRoomListEachResponse> chattingRooms;

    public ChattingRoomListResponse(Long userId, List<ChattingRoomListEachResponse> chattingRooms) {
        this.userId = userId;
        this.chattingRooms = chattingRooms;
    }
}
