package konkuk.gonggoose.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BulletinGetDto {
    private Long bulletin_id;
    private String status;
    private String title;
    private Long current_user_number;
    private Long max_user_number;
    private String image_url;
}
