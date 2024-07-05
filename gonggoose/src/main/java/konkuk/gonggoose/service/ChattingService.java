package konkuk.gonggoose.service;

import konkuk.gonggoose.common.dto.ChattingMessageDto;
import konkuk.gonggoose.common.dto.ChattingMessageResponse;
import konkuk.gonggoose.common.dto.ChattingRoomListEachResponse;
import konkuk.gonggoose.common.dto.ChattingRoomListResponse;
import konkuk.gonggoose.domain.ChattingMessage;
import konkuk.gonggoose.dao.ChattingRepository;
import konkuk.gonggoose.domain.ChattingRoom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChattingService {

    private final ChattingRepository chattingRepository;

    @Transactional
    public ChattingMessageResponse receiveMessage(Long userId, Long chattingRoomId, ChattingMessageDto chattingMessageDto) {
        ChattingMessage chattingMessage = new ChattingMessage(userId, chattingRoomId, chattingMessageDto.getContent());

        Long id = chattingRepository.save(chattingMessage);
        ChattingMessage findMessage = chattingRepository.findChattingMessageById(id);
        //TODO 작성 유저 조회

        return ChattingMessageResponse.create(findMessage);
    }

    public ChattingRoomListResponse getChattingRoomList(Long userId){
        List<ChattingRoom> chattingRooms = chattingRepository.findChattingRoomsByUserId(userId);

        List<Long> chattingRoomIds = chattingRooms.stream()
                .map(ChattingRoom::getId)
                .toList();

        List<ChattingMessage> lastMessagesInChattingRooms = chattingRepository.findLastMessagesInChattingRooms(chattingRoomIds);
        List<ChattingRoomListEachResponse> chattingRoomsDatas;
        if(lastMessagesInChattingRooms.size() > 0){
            Map<Long, String> lastMessageOfChattingRoomMap = chattingRepository.findLastMessagesInChattingRooms(chattingRoomIds).stream()
                    .collect(Collectors.toMap(ChattingMessage::getChattingRoomId, ChattingMessage::getContent));
            chattingRoomsDatas = createChattingRoomListMessage(chattingRooms, lastMessageOfChattingRoomMap);
        }else {
            chattingRoomsDatas = createChattingRoomListMessage(chattingRooms, null);
        }
        return new ChattingRoomListResponse(userId, chattingRoomsDatas);
    }

    public List<ChattingRoomListEachResponse> createChattingRoomListMessage(List<ChattingRoom> chattingRooms, Map<Long, String> lastMessageOfChattingRoomMap){
        List<ChattingRoomListEachResponse> data = new ArrayList<>();
        for (ChattingRoom chattingRoom : chattingRooms) {
            String lastMessage = lastMessageOfChattingRoomMap.getOrDefault(chattingRoom.getId(), null);
            data.add(ChattingRoomListEachResponse.create(chattingRoom, lastMessage));
        }
        return data;
    }

}
