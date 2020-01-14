package vn.vlong.booklibrary.api.eventsource.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.vlong.booklibrary.api.eventsource.domain.EventSource;
import vn.vlong.booklibrary.api.eventsource.repository.IEventSourceRepository;
import vn.vlong.booklibrary.api.shared.domain.event.Event;

import java.util.UUID;

@Service
@Slf4j
public class EventSourceService {
    private IEventSourceRepository eventSourceRepository;

    @Autowired
    public EventSourceService(IEventSourceRepository eventSourceRepository) {
        this.eventSourceRepository = eventSourceRepository;
    }

    public void addEventStore(Event event) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String payload = objectMapper.writeValueAsString(event);
            EventSource eventSource = new EventSource(UUID.randomUUID(), event.getAggregateId(),
                    event.getClass().getName(), payload, event.getVersion());
            EventSource result = eventSourceRepository.insert(eventSource);
        } catch (JsonProcessingException e) {
            log.error("Can not serialize event source object.");
        }
    }
}
