package konkuk.gonggoose.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class BulletinImageDto {
    private String imageUrl;
    private Long bulletinId;
}

