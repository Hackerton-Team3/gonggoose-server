package konkuk.gonggoose.dao;

import konkuk.gonggoose.dto.PatchImageUrlRequest;
import konkuk.gonggoose.dto.SignupRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Repository
public class UserDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public boolean isExistedUser(long kakaoId) {
        log.info("[UserDao.isExistedUser]");
        String sql = "select exists(select kakao_id from user where kakao_id=:kakao_id)";
        Map<String, Object> param = Map.of("kakao_id", kakaoId);
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, param, boolean.class));
    }

    public long signup(SignupRequest signupRequest) {
        log.info("[UserDao.signup]");

        if (isExistedUser(Long.parseLong(signupRequest.getKakaoId()))) {
            return -1;
        }

        String sql = "insert into user(kakao_id, nickname, image_url, school_name, school_email)" +
                "values(:kakaoId, :nickname, :imageUrl, :schoolName, :schoolEmail)";

        SqlParameterSource param = new BeanPropertySqlParameterSource(signupRequest);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, param, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public void updateImageUrl(PatchImageUrlRequest patchImageUrlRequest) {
        log.info("[UserDao.updateImageUrl]");
        String sql = "update user set image_url=:image_url where token=:token";
        Map<String, Object> param = Map.of(
                "image_url", patchImageUrlRequest.getImageUrl(),
                "token", patchImageUrlRequest.getAccessToken());
        jdbcTemplate.update(sql, param);
    }
}
