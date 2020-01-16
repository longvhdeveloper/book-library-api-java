package vn.vlong.booklibrary.api.shared.eventbus;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import vn.vlong.booklibrary.api.shared.domain.event.Event;

@Component
@Slf4j
public class EventSourcePublisher<T extends Event> implements IEventSourcePublisher<T> {

    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public EventSourcePublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void publish(T event) {
        applicationEventPublisher.publishEvent(event);
    }
}
