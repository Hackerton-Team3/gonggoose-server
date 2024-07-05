package konkuk.gonggoose.common.event;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChatRoomCreateEvent {
    private String imageUrl;
    private String title;
    private Long bulletinId;
    private Long chattingRoomTopicId;

    public ChatRoomCreateEvent(String imageUrl, String title, Long bulletinId, Long chattingRoomTopicId) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.bulletinId = bulletinId;
        this.chattingRoomTopicId = chattingRoomTopicId;
    }

}
