package konkuk.gonggoose.common.event;

import konkuk.gonggoose.service.ChattingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

@Slf4j
@RequiredArgsConstructor
public class ChatRoomEventHandler {

    private final ChattingService chattingService;

    @Async
    @EventListener
    public void handleCreateChatRoom(ChatRoomCreateEvent event) {
        chattingService.createChatRoom(event.getUserId(), event.getBulletinId(), event.getTitle(), event.getImageUrl(), event.getChattingRoomTopicId());
    }
}
