package konkuk.gonggoose.common.event;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class ChatRoomCreateEvent {
    private Long userId;
    private String imageUrl;
    private String title;
    private Long bulletinId;
    private String chattingRoomTopicId;

    public ChatRoomCreateEvent(Long userId, String imageUrl, String title, Long bulletinId, String chattingRoomTopicId) {
        this.userId = userId;
        this.imageUrl = imageUrl;
        this.title = title;
        this.bulletinId = bulletinId;
        this.chattingRoomTopicId = chattingRoomTopicId;
    }

}
