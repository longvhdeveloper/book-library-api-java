package vn.vlong.booklibrary.api.shared.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import vn.vlong.booklibrary.api.eventsource.domain.EventSource;
import vn.vlong.booklibrary.api.eventsource.repository.IEventSourceRepository;
import vn.vlong.booklibrary.api.shared.domain.event.Event;

import java.util.UUID;

@Component
@Slf4j
public class EventSourcePublisher<T extends Event> implements IEventSourcePublisher<T> {

    private ApplicationEventPublisher applicationEventPublisher;
    private IEventSourceRepository eventSourceRepository;

    @Autowired
    public EventSourcePublisher(ApplicationEventPublisher applicationEventPublisher,
                                IEventSourceRepository eventSourceRepository) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.eventSourceRepository = eventSourceRepository;
    }

    @Override
    public void publish(T event) {
        saveEventSource(event);
        applicationEventPublisher.publishEvent(event);
    }

    private void saveEventSource(Event event) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String payload = objectMapper.writeValueAsString(event);
            EventSource eventSource = new EventSource(UUID.randomUUID(), event.getAggregateId(),
                    event.getClass().getName(), payload);
            EventSource result = eventSourceRepository.insert(eventSource);
            System.out.println("EVENT: " + result);
        } catch (JsonProcessingException e) {
            log.error("Can not serialize event source object.");
        }
    }
}
