package vn.vlong.booklibrary.api.eventsource.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.vlong.booklibrary.api.eventsource.domain.entity.EventSource;
import vn.vlong.booklibrary.api.eventsource.repository.IEventSourceRepository;
import vn.vlong.booklibrary.api.shared.domain.event.Event;

@Service
public class EventSourceService {

  private IEventSourceRepository eventSourceRepository;
  private ObjectMapper objectMapper;

  @Autowired
  public EventSourceService(IEventSourceRepository eventSourceRepository,
      ObjectMapper objectMapper) {
    this.eventSourceRepository = eventSourceRepository;
    this.objectMapper = objectMapper;
  }

  public void storeEvents(List<Event> events) {
    events.forEach(this::storeEvent);
  }

  public void storeEvent(Event event) {
    try {
      String payload = objectMapper.writeValueAsString(event);
      EventSource eventSource = new EventSource(event.getAggregateId(), event.getStream(),
          event.getClass().getName(), event.getVersion(), payload);
      eventSourceRepository.save(eventSource);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  public List<Event> loadEvents(String aggregateId, String stream) {
    List<EventSource> eventSources = eventSourceRepository.findByAggregateId(aggregateId, stream);
    return eventSources.stream().map(EventSource::toDomainEvent).collect(Collectors.toList());
  }
}
