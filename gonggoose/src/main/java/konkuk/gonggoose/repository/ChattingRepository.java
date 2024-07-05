package konkuk.gonggoose.repository;

import konkuk.gonggoose.domain.ChattingMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ChattingRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public Long save(ChattingMessage chattingMessage){
        String sql = "insert into chatting_message(user_id, chatting_room_id, content)" +
                " values(:userId, :chattingRoomId, :content)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource param = new BeanPropertySqlParameterSource(chattingMessage);

        jdbcTemplate.update(sql, param, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public ChattingMessage findById(Long id){
        String sql = "select chatting_message_id as id, content, chatting_room_id as chattingRoomId, user_id as userId, create_at as createAt" +
                " from chatting_message where chatting_message_id = :id";

        Map<String, Long> param = Map.of("id", id);

        return jdbcTemplate.queryForObject(sql, param, chattingMessageRowMapper());
    }

    private RowMapper<ChattingMessage> chattingMessageRowMapper(){
        return BeanPropertyRowMapper.newInstance(ChattingMessage.class);
    }
}
