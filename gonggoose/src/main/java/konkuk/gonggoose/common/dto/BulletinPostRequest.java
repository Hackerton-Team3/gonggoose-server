package konkuk.gonggoose.common.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize
public class BulletinPostRequest{
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private String deadline;
    private Long writer_id;
    private String title;
    private Long max_user_number;
    private Long expected_price;
    private String content;
}
