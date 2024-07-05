package konkuk.gonggoose.common.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.context.ApplicationEvent;
public class ChatRoomCreateEvent extends ApplicationEvent {
    private String imageUrl;
    private String title;
    private Long bulletinId;

    public ChatRoomCreateEvent(Object source) {
        super(source);
    }

    public ChatRoomCreateEvent(Object source, String imageUrl, String title, Long bulletinId) {
        super(source);
        this.imageUrl = imageUrl;
        this.title = title;
        this.bulletinId = bulletinId;
    }

}
