package konkuk.gonggoose.dao;

import konkuk.gonggoose.domain.ChattingMessage;
import konkuk.gonggoose.domain.ChattingRoom;
import konkuk.gonggoose.domain.UserChattingRoom;
import lombok.RequiredArgsConstructor;
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

    public Long save(ChattingRoom chattingRoom){
        String sql = "insert into chatting_room(bulletin_id, title, image_url, chatting_room_topic_id)" +
                " values(:bulletinId, :title, :imageUrl, :chattingRoomTopicId)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource param = new BeanPropertySqlParameterSource(chattingRoom);

        jdbcTemplate.update(sql, param, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public Long save(UserChattingRoom userChattingRoom){
        String sql = "insert into user_chatting_room(user_id, chatting_room_id)" +
                " values(:userId, :chattingRoomId)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource param = new BeanPropertySqlParameterSource(userChattingRoom);

        jdbcTemplate.update(sql, param, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public ChattingMessage findChattingMessageById(Long id){
        String sql = "select * from chatting_message where chatting_message_id = :id";

        Map<String, Long> param = Map.of("id", id);

        return jdbcTemplate.queryForObject(sql, param, chattingMessageRowMapper());
    }

    public List<ChattingRoom> findChattingRoomsByUserId(Long userId){
        String sql = "select * from chatting_room where chatting_room_id in (select chatting_room_id from user_chatting_room where user_id = :userId)";

        Map<String, Long> param = Map.of("userId", userId);

        return jdbcTemplate.query(sql, param, chattingRoomRowMapper());
    }

    public ChattingRoom findChattingRoomsByBulletinId(Long bulletinId){
        String sql = "select * from chatting_room where bulletin_id = :bulletinId";

        Map<String, Long> param = Map.of("bulletinId", bulletinId);

        return jdbcTemplate.queryForObject(sql, param, chattingRoomRowMapper());
    }

    public List<ChattingMessage> findLastMessagesInChattingRooms(List<Long> chattingRoomIds){
        String sql = "SELECT * FROM chatting_message cm " +
                "INNER JOIN (" +
                "    SELECT chatting_room_id, MAX(create_at) AS max_create_at " +
                "    FROM chatting_message " +
                "    WHERE chatting_room_id IN (:chattingRoomIds) " +
                "    GROUP BY chatting_room_id" +
                ") latest ON cm.chatting_room_id = latest.chatting_room_id AND cm.create_at = latest.max_create_at " +
                "ORDER BY cm.create_at DESC";

        Map<String, Object> param = Collections.singletonMap("chattingRoomIds", chattingRoomIds);

        return jdbcTemplate.query(sql, param, chattingMessageRowMapper());
    }

    public ChattingRoom findChattingRoomById(Long chattingRoomId){
        String sql = "select * from chatting_room where chatting_room_id = :chattingRoomId";

        Map<String, Long> param = Map.of("chattingRoomId", chattingRoomId);

        return jdbcTemplate.queryForObject(sql, param, chattingRoomRowMapper());
    }

    public List<ChattingMessage> findChattingMessageListByChattingRoom(Long chattingRoomId){
        String sql = "select * from chatting_message where chatting_room_id = :chattingRoomId";

        Map<String, Long> param = Map.of("chattingRoomId", chattingRoomId);

        return jdbcTemplate.query(sql, param, chattingMessageRowMapper());
    }

    public String getChattingRoomTopicId(Long chattingRoomId){
        String sql = "SELECT chatting_room_topic_id FROM chatting_room WHERE chatting_room_id = :chattingRoomId";

        Map<String, Long> param = Collections.singletonMap("chattingRoomId", chattingRoomId);

        return jdbcTemplate.queryForObject(sql, param, String.class);
    }


    private RowMapper<ChattingMessage> chattingMessageRowMapper(){
        return (rs, rn) -> {
            return ChattingMessage.builder()
                    .id(rs.getLong("chatting_message_id"))
                    .userId(rs.getLong("user_id"))
                    .chattingRoomId(rs.getLong("chatting_room_id"))
                    .content(rs.getString("content"))
                    .createAt(rs.getTimestamp("create_at").toLocalDateTime())
                    .build();
        };
    }

    private RowMapper<ChattingRoom> chattingRoomRowMapper() {
        return (rs, rn) -> {
            return ChattingRoom.builder()
                    .id(rs.getLong("chatting_room_id"))
                    .bulletinId(rs.getLong("bulletin_id"))
                    .title(rs.getString("title"))
                    .imageUrl(rs.getString("image_url"))
                    .description(rs.getString("description"))
                    .chattingRoomTopicId(rs.getString("chatting_room_topic_id"))
                    .createAt(rs.getTimestamp("create_at").toLocalDateTime())
                    .updateAt(rs.getTimestamp("update_at").toLocalDateTime())
                    .build();
        };
    }
}
