package konkuk.gonggoose.common.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BulletinPostResponse {
    private Long bulletin_id;
    private String chat_room_uuid;
}
