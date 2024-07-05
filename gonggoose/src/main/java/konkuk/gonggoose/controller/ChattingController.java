package konkuk.gonggoose.controller;

import konkuk.gonggoose.common.dto.ChattingMessageDto;
import konkuk.gonggoose.common.dto.ChattingMessageResponse;
import konkuk.gonggoose.common.response.BaseResponse;
import konkuk.gonggoose.service.ChattingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChattingController {

    private final ChattingService chattingService;

    @MessageMapping("/users/{userId}/chattingRooms/{chattingRoomId}")
    @SendTo("/topic/chattingRooms/{chattingRoomId}")
    public ChattingMessageResponse receiveMessage(@DestinationVariable Long userId,
                                                  @DestinationVariable Long chattingRoomId,
                                                  @Payload ChattingMessageDto chattingMessageDto){
        ChattingMessageResponse responseData = chattingService.receiveMessage(userId, chattingRoomId, chattingMessageDto);
        return responseData;
    }

    @GetMapping("/users/{userId}/chattingRooms")
    public void chattingRoomList(@PathVariable Long userId){
        chattingService.getChattingRoomList();
    }
}
