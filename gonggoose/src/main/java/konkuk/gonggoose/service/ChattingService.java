package konkuk.gonggoose.service;

import konkuk.gonggoose.common.dto.*;
import konkuk.gonggoose.domain.ChattingMessage;
import konkuk.gonggoose.dao.ChattingRepository;
import konkuk.gonggoose.domain.ChattingRoom;
import konkuk.gonggoose.domain.UserChattingRoom;
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
        if(chattingRooms.size() == 0){
            return new ChattingRoomListResponse(userId, new ArrayList<>());
        }

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

    public ChattingRoomDetailResponse getChattingRoomDetail(Long userId, Long chatRoomId){
        ChattingRoom chattingRoom = chattingRepository.findChattingRoomById(chatRoomId);
        List<ChattingMessage> chattingMessageList = chattingRepository.findChattingMessageListByChattingRoom(chatRoomId);

        List<ChattingMessageResponse> messageDatas = new ArrayList<>();
        for (ChattingMessage chattingMessage : chattingMessageList) {
            messageDatas.add(ChattingMessageResponse.create(chattingMessage));
        }

        return ChattingRoomDetailResponse.create(chattingRoom, messageDatas);
    }

    public List<ChattingRoomListEachResponse> createChattingRoomListMessage(List<ChattingRoom> chattingRooms, Map<Long, String> lastMessageOfChattingRoomMap){
        List<ChattingRoomListEachResponse> data = new ArrayList<>();
        for (ChattingRoom chattingRoom : chattingRooms) {
            String lastMessage = lastMessageOfChattingRoomMap.getOrDefault(chattingRoom.getId(), null);
            data.add(ChattingRoomListEachResponse.create(chattingRoom, lastMessage));
        }
        return data;
    }

    public String getChattingRoomTopicId(Long chattingRoomId){
        return chattingRepository.getChattingRoomTopicId(chattingRoomId);
    }

    //==이벤트 메서드==
    public void createChatRoom(Long userId, Long bulletinId, String title, String imageUrl, String chattingRoomTopicId){
        ChattingRoom chattingRoom = ChattingRoom.create(bulletinId, title, imageUrl, chattingRoomTopicId);
        Long chattingRoomId = chattingRepository.save(chattingRoom);

        UserChattingRoom userChattingRoom = UserChattingRoom.create(userId, chattingRoomId);
        chattingRepository.save(userChattingRoom);
    }

}
