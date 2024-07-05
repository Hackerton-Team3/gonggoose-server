package konkuk.gonggoose.controller;

import konkuk.gonggoose.common.dto.ChattingMessageDto;
import konkuk.gonggoose.common.dto.ChattingMessageResponse;
import konkuk.gonggoose.common.dto.ChattingRoomDetailResponse;
import konkuk.gonggoose.common.dto.ChattingRoomListResponse;
import konkuk.gonggoose.common.response.BaseResponse;
import konkuk.gonggoose.service.ChattingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChattingController {

    private final ChattingService chattingService;

    @MessageMapping("/users/{userId}/chattingRooms/{chattingRoomId}")
    @SendTo("/topic/chattingRooms/{chattingRoomTopicId}")
    public ChattingMessageResponse receiveMessage(@DestinationVariable Long userId,
                                                  @DestinationVariable Long chattingRoomId,
                                                  @Payload ChattingMessageDto chattingMessageDto){
        ChattingMessageResponse responseData = chattingService.receiveMessage(userId, chattingRoomId, chattingMessageDto);
        String chattingRoomTopicId = chattingService.getChattingRoomTopicId(chattingRoomId);

        return responseData;
    }

    @GetMapping("/users/{userId}/chattingRooms")
    public ResponseEntity<BaseResponse> chattingRoomList(@PathVariable Long userId){
        ChattingRoomListResponse data = chattingService.getChattingRoomList(userId);

        BaseResponse<ChattingRoomListResponse> responseData = new BaseResponse<>(data);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("/users/{userId}/chattingRooms/{chatRoomId}")
    public ResponseEntity<BaseResponse<ChattingRoomDetailResponse>> chattingRoomDetail(@PathVariable Long userId,
                                                                                       @PathVariable Long chatRoomId){
        ChattingRoomDetailResponse data = chattingService.getChattingRoomDetail(userId, chatRoomId);
        BaseResponse<ChattingRoomDetailResponse> responseData = new BaseResponse<>(data);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping
}
