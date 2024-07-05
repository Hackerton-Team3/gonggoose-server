package konkuk.gonggoose.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BulletinResponse {
    private String title;
    private long currentUserNumber;
    private long maxUserNumber;
    private String status;
}
