package vn.vlong.booklibrary.api.shared.eventbus;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import vn.vlong.booklibrary.api.eventsource.service.EventSourceService;
import vn.vlong.booklibrary.api.shared.domain.event.Event;

@Component
@Slf4j
public class EventSourcePublisher<T extends Event> implements IEventSourcePublisher<T> {

    private ApplicationEventPublisher applicationEventPublisher;
    private EventSourceService eventSourceService;

    @Autowired
    public EventSourcePublisher(ApplicationEventPublisher applicationEventPublisher,
                                EventSourceService eventSourceService) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.eventSourceService = eventSourceService;
    }

    @Override
    public void publish(T event) {
        eventSourceService.addEventStore(event);
        applicationEventPublisher.publishEvent(event);
    }
}
