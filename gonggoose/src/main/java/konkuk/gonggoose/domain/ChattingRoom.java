package konkuk.gonggoose.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class ChattingRoom {
    private Long id;
    private Long bulletinId;
    private String title;
    private String imageUrl;
    private String description;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}
