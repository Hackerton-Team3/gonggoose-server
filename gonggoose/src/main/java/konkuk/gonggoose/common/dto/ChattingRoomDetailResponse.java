package konkuk.gonggoose.common.dto;

import konkuk.gonggoose.domain.ChattingRoom;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ChattingRoomDetailResponse {
    private Long chattingRoomId;
    private String chattingRoomImageUrl;
    private String title;
    private List<ChattingMessageResponse> messages;

    public static ChattingRoomDetailResponse create(ChattingRoom chattingRoom, List<ChattingMessageResponse> messageDatas){
        return ChattingRoomDetailResponse.builder()
                .chattingRoomId(chattingRoom.getId())
                .chattingRoomImageUrl(chattingRoom.getImageUrl())
                .title(chattingRoom.getTitle())
                .messages(messageDatas)
                .build();
    }
}
