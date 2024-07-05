package konkuk.gonggoose.common.dto;

import konkuk.gonggoose.domain.ChattingRoom;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChattingRoomListEachResponse {
    private Long chattingRoomId;
    private String chattingRoomImageUrl;
    private String title;
    private String lastComment;

    public static ChattingRoomListEachResponse create(ChattingRoom chattingRoom, String lastMessage) {
        return ChattingRoomListEachResponse.builder()
                .chattingRoomId(chattingRoom.getId())
                .chattingRoomImageUrl(chattingRoom.getImageUrl())
                .title(chattingRoom.getTitle())
                .lastComment(lastMessage)
                .build();
    }
}
