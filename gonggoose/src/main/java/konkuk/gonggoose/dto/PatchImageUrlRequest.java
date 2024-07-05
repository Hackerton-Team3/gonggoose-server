package konkuk.gonggoose.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatchImageUrlRequest {
    private String imageUrl;
    private String accessToken;
}
