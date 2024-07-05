package konkuk.gonggoose.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserBulletinDto {
    private Long userId;
    private Long bulletinId;
}
