package konkuk.gonggoose.dao;

import konkuk.gonggoose.dto.PatchImageUrlRequest;
import konkuk.gonggoose.dto.SignupRequest;
import konkuk.gonggoose.dto.SignupResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Repository
public class UserDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<SignupResponse> isExistedUser(long kakaoId) {
        log.info("[UserDao.isExistedUser]");
        String sql = "select exists(select kakao_id from user where kakao_id=:kakao_id)";
        Map<String, Object> param = Map.of("kakao_id", kakaoId);

        if (Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, param, boolean.class))) {
            sql = "select user_id from user where kakao_id=:kakao_id";
            return jdbcTemplate.query(sql, param,
                    (rs, rowNum) -> new SignupResponse(
                            rs.getLong("user_id"))
            );
        }
        return null;
    }

    public long signup(SignupRequest signupRequest) {
        log.info("[UserDao.signup]");

        if (isExistedUser(Long.parseLong(signupRequest.getKakaoId())) == null) {
            return -1;
        }

        String sql = "insert into user(kakao_id, nickname, image_url, school_name, school_email)" +
                "values(:kakaoId, :nickname, :imageUrl, :schoolName, :schoolEmail)";

        SqlParameterSource param = new BeanPropertySqlParameterSource(signupRequest);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, param, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public void updateImageUrl(PatchImageUrlRequest patchImageUrlRequest, long userId) {
        log.info("[UserDao.updateImageUrl]");
        String sql = "update user set image_url=:image_url where user_id=:user_id";
        Map<String, Object> param = Map.of(
                "image_url", patchImageUrlRequest.getImageUrl(),
                "user_id", userId);
        jdbcTemplate.update(sql, param);
    }
}
