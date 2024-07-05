package konkuk.gonggoose.dao;

import konkuk.gonggoose.common.dto.BulletinGetDto;
import konkuk.gonggoose.common.dto.BulletinPostRequest;
import konkuk.gonggoose.common.dto.UserBulletinDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
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
public class BulletinDao {
    private NamedParameterJdbcTemplate jdbcTemplate;

    public BulletinDao(DataSource dataSource) {
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

    public List<BulletinGetDto> getBulletinListByKeyword(String keyword) {
        String sql = "SELECT b.bulletin_id, b.title, b.status, b.max_user_number,\n" +
                "       (SELECT COUNT(*)\n" +
                "        FROM user_bulletin\n" +
                "        WHERE bulletin_id = b.bulletin_id) AS current_user_count,\n" +
                "        (SELECT image_url\n" +
                "        FROM bulletin_image\n" +
                "        WHERE bulletin_id = b.bulletin_id\n" +
                "        LIMIT 1) AS image_url FROM bulletin as b WHERE b.title LIKE :keyword";

        MapSqlParameterSource param = new MapSqlParameterSource().addValue("keyword", "%" + keyword + "%");
        return jdbcTemplate.query(sql, param, (resultSet, rowNum) ->{
            BulletinGetDto dto = new BulletinGetDto();
            dto.setBulletin_id(Long.parseLong(resultSet.getString("bulletin_id")));
            dto.setTitle(resultSet.getString("title"));
            dto.setStatus(resultSet.getString("status"));
            dto.setMax_user_number(Long.parseLong(resultSet.getString("max_user_number")));
            dto.setCurrent_user_number(Long.parseLong(resultSet.getString("current_user_count")));
            dto.setImage_url(resultSet.getString( "image_url"));
            return dto;
        });
    }

    public long createUserBulletin(UserBulletinDto dto){
        String sql = "insert into user_bulletin(user_right, user_id, bulletin_id) values('OWNER', :userId, :bulletinId)";
        SqlParameterSource param = new BeanPropertySqlParameterSource(dto);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, param, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }
}
