package konkuk.gonggoose.common.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatRoomCreateEventPublisher {
    private final ApplicationEventPublisher publisher;

    public void publish(ChatRoomCreateEvent event){
        publisher.publishEvent(event);
    }
}
