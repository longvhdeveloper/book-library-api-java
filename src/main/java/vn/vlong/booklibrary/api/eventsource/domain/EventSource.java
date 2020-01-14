package vn.vlong.booklibrary.api.eventsource.domain;

import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import vn.vlong.booklibrary.api.shared.domain.entity.IEntity;

import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Document(collection = "event-source")
@Getter
public class EventSource implements IEntity<EventSource> {

    @Id
    private String id;

    private String aggregateId;

    private String type;

    private String payload;

    private Date occurred;

    private int version;

    public EventSource(UUID uuid, UUID aggregateId, String type, String payload, int version) {
        this.id = uuid.toString();
        this.aggregateId = aggregateId.toString();
        this.type = type;
        this.payload = payload;
        this.occurred = new Date();
        this.version = version;
    }

    @Override
    public boolean isSameIdentity(EventSource other) {
        if (Objects.isNull(other)) {
            return false;
        }
        return id.equals(other.getId());
    }
}
