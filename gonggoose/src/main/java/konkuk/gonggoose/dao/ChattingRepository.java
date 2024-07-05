package konkuk.gonggoose.dao;

import konkuk.gonggoose.domain.ChattingMessage;
import konkuk.gonggoose.domain.ChattingRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
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

    public ChattingMessage findChattingMessageById(Long id){
        String sql = "select chatting_message_id as id, content, chatting_room_id as chattingRoomId, user_id as userId, create_at as createAt" +
                " from chatting_message where chatting_message_id = :id";

        Map<String, Long> param = Map.of("id", id);

        return jdbcTemplate.queryForObject(sql, param, chattingMessageRowMapper());
    }

    public List<ChattingRoom> findChattingRoomsByUserId(Long userId){
        String sql = "select chatting_room_id as id, title, image_url as imageUrl, description, bulletin_id as bulletinId from chatting_room" +
                " where chatting_room_id in (select chatting_room_id from user_chatting_room where user_id = :userId)";

        Map<String, Long> param = Map.of("userId", userId);

        return jdbcTemplate.query(sql, param, chattingRoomRowMapper());
    }

    public List<ChattingMessage> findLastMessagesInChattingRooms(List<Long> chattingRoomIds){
        String sql = "SELECT max(chatting_message_id) AS id, content, chatting_room_id AS chattingRoomId, user_id AS userId, max(create_at) AS createAt " +
                "FROM chatting_message " +
                "WHERE chatting_room_id IN (:chattingRoomIds) " +
                "GROUP BY chatting_room_id, content, chattingRoomId, userId " +  // 모든 선택한 열을 GROUP BY 절에 추가합니다.
                "ORDER BY createAt DESC";

        Map<String, Object> param = Collections.singletonMap("chattingRoomIds", chattingRoomIds);

        return jdbcTemplate.query(sql, param, chattingMessageRowMapper());
    }

    private RowMapper<ChattingMessage> chattingMessageRowMapper(){
        return BeanPropertyRowMapper.newInstance(ChattingMessage.class);
    }

    private RowMapper<ChattingRoom> chattingRoomRowMapper(){
        return BeanPropertyRowMapper.newInstance(ChattingRoom.class);
    }
}
