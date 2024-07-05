package konkuk.gonggoose.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ChatRoomCreateEventDTO {
    private String thumbnailUrl;
    private String title;
    private Long bulletinId;
}
