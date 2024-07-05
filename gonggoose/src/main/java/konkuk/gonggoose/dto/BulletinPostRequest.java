package konkuk.gonggoose.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BulletinPostRequest {
    LocalDateTime deadline;
    private Long writer_id;
    private String title;
    private Integer max_user_number;
    private Integer expected_price;
    private String content;
}
