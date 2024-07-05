package konkuk.gonggoose.dao;

import konkuk.gonggoose.common.dto.BulletinGetDTO;
import konkuk.gonggoose.common.dto.BulletinPostRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Repository
public class BulletinDAO {
    NamedParameterJdbcTemplate jdbcTemplate;

    public BulletinDAO(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public long createBulletin(BulletinPostRequest request) {
        String sql = "insert into bulletin(title, content, price, max_user_number, deadline, writer_id) values(:title, :content, :expected_price, :max_user_number, :deadline, :writer_id)";
        SqlParameterSource param = new BeanPropertySqlParameterSource(request);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, param, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public void deleteBulletin(Long bulletinId) {
        String sql = "delete from bulletin where bulletin_id = :bulletinId";
        Map<String, Long> param = Map.of("bulletinId", bulletinId);
        jdbcTemplate.update(sql, param);
    }

    public List<BulletinGetDTO> getBulletinListByKeyword(String keyword) {
        String sql = "SELECT FROM bulletin WHERE title LIKE '%keyword%'";

        return null;
    }
}
