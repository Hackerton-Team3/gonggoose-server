package konkuk.gonggoose.domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChattingRoom {
    private Long id;
    private Long bulletinId;
    private String title;
    private String description;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}
