package vn.me.vlong.booklibraryapi.modules.shared.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import vn.me.vlong.booklibraryapi.modules.shared.model.event.Event;
import vn.me.vlong.booklibraryapi.modules.eventsource.model.EventSource;
import vn.me.vlong.booklibraryapi.modules.eventsource.repository.IEventSourceRepository;

import java.util.Date;
import java.util.UUID;

@Slf4j
@Component
public class EventPublisher implements IEventPublisher {

    private ApplicationEventPublisher applicationEventPublisher;
    private IEventSourceRepository eventSourceRepository;

    public EventPublisher(ApplicationEventPublisher applicationEventPublisher,
                          IEventSourceRepository eventSourceRepository) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.eventSourceRepository = eventSourceRepository;
    }

    @Override
    public void publish(Event event) {
        saveEventSource(event);
        applicationEventPublisher.publishEvent(event);
    }

    private void saveEventSource(Event event) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String payload = objectMapper.writeValueAsString(event);
            EventSource eventSource = new EventSource(UUID.randomUUID(), event.getClass().getName(),
                    event.getAggregateId(), payload,
                    new Date());
            EventSource result = eventSourceRepository.insert(eventSource);
            System.out.println("EVENT: " + result);
        } catch (JsonProcessingException e) {
            log.error("Can not serialize event source object.");
        }
    }
}
