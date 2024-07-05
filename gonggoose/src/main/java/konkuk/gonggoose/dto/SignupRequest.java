package konkuk.gonggoose.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignupRequest {
    private String kakaoId;
    private String nickname;
    private String imageUrl;
    private String schoolName;
    private String schoolEmail;
}
