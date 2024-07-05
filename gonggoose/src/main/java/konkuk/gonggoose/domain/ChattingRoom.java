package konkuk.gonggoose.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChattingRoom {
    private Long id;
    private Long bulletinId;
    private String title;
    private String imageUrl;
    private String description;
    private String chattingRoomTopicId;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    public static ChattingRoom create(Long bulletinId, String title, String imageUrl, String chattingRoomTopicId){

        return ChattingRoom.builder()
                .bulletinId(bulletinId)
                .title(title)
                .imageUrl(imageUrl)
                .chattingRoomTopicId(chattingRoomTopicId)
                .build();
    }
}
