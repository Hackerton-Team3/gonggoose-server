package konkuk.gonggoose.dao;

import konkuk.gonggoose.dto.BulletinImageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Repository
public class BulletinImageDao {

    NamedParameterJdbcTemplate jdbcTemplate;

    public BulletinImageDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public long save(BulletinImageDto dto) {
        String sql = "insert into bulletin_image(image_url, bulletin_id) values(:imageUrl, :bulletinId)";
        SqlParameterSource param = new BeanPropertySqlParameterSource(dto);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, param, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }
}
