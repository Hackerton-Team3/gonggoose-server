package konkuk.gonggoose.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Map;

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
}
