package konkuk.gonggoose.service;

import konkuk.gonggoose.common.dto.ChattingMessageDto;
import konkuk.gonggoose.common.dto.ChattingMessageResponse;
import konkuk.gonggoose.domain.ChattingMessage;
import konkuk.gonggoose.repository.ChattingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChattingService {

    private final ChattingRepository chattingRepository;

    public ChattingMessageResponse receiveMessage(Long userId, Long chattingRoomId, ChattingMessageDto chattingMessageDto) {
        ChattingMessage chattingMessage = new ChattingMessage(userId, chattingRoomId, chattingMessageDto.getContent());

        Long id = chattingRepository.save(chattingMessage);
        ChattingMessage findMessage = chattingRepository.findById(id);
        //TODO 작성 유저 조회

        return ChattingMessageResponse.create(findMessage);
    }

    public void getChattingRoomList(){

    }
}
