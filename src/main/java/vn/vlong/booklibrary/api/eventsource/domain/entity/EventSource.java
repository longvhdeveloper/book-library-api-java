package vn.vlong.booklibrary.api.eventsource.domain.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import vn.vlong.booklibrary.api.shared.domain.event.Event;

@Entity
@Table(name = "event_source", indexes = {
    @Index(name = "idx_aggregate_id", columnList = "aggregate_id"),
    @Index(name = "idx_stream", columnList = "stream")
})
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
public class EventSource {

  private static final ObjectMapper mapper = new ObjectMapper();

  @Id
  private String id;

  @Column(name = "aggregate_id", nullable = false)
  private String aggregateId;

  @Column(nullable = false)
  private String stream;

  @Column(name = "type_name", nullable = false)
  private String typeName;


  private long version;

  @Column(nullable = false)
  private String payload;

  @Column(name = "occurred_on", nullable = false)
  @CreatedDate
  private Date occurredOn;

  public EventSource(String aggregateId, String stream, String typeName, long version,
      String payload) {
    this.id = UUID.randomUUID().toString();
    this.aggregateId = aggregateId;
    this.stream = stream;
    this.typeName = typeName;
    this.version = version;
    this.payload = payload;
  }

  public <T extends Event> Event toDomainEvent() {
    Class<T> eventClass = null;

    try {
      eventClass = (Class<T>) Class.forName(this.typeName);
    } catch (Exception e) {
      throw new IllegalStateException("Class load error, because: " + e.getMessage());
    }

    T domainEvent = null;
    try {
      domainEvent = mapper.readValue(this.payload, eventClass);
    } catch (IOException e) {
      throw new IllegalStateException("Event deserialization error, because: " + e.getMessage());
    }

    return domainEvent;
  }
}
