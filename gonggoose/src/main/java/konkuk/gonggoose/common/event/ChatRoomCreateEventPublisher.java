package konkuk.gonggoose.common.event;

import konkuk.gonggoose.common.dto.ChatRoomCreateEventDTO;
import konkuk.gonggoose.common.event.ChatRoomCreateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatRoomCreateEventPublisher {
    private final ApplicationEventPublisher publisher;

    public void publish(ChatRoomCreateEvent event){
        publisher.publishEvent(event);
    }

}
