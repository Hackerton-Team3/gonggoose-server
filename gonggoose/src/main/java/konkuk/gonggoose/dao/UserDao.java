package konkuk.gonggoose.dao;

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

    public long signup(SignupRequest signupRequest, String createdAt, String updatedAt) {
        log.info("[UserDao.signup]");

        String sql = "insert into user(kakao_id, nickname, image_url, school_name, school_email, token)" +
                "values(:kakao_id, :nickname, :image_url, :school_name, :school_email, :token)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
//        SqlParameterSource param = new BeanPropertySqlParameterSource(signupRequest);
        Map<String, Object> param = Map.of( "user_id", Objects.requireNonNull(keyHolder.getKey()).longValue(),
                "kakao_id", signupRequest.getKakaoId(),
                "nickname", signupRequest.getNickname(),
                "image_url", signupRequest.getImageUrl(),
                "school_name", signupRequest.getSchoolName(),
                "school_email", signupRequest.getSchoolEmail(),
                "token", signupRequest.getAccessToken(),
                "created_at", createdAt,
                "updated_at", updatedAt);
        jdbcTemplate.update(sql, param);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }
}
