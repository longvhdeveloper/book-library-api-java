package vn.vlong.booklibrary.api.shared.domain.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;

@Getter
public abstract class Event extends ApplicationEvent implements IEvent {
    private UUID aggregateId;
    private int version;

    public Event(Object source, UUID aggregateId, int version) {
        super(source);
        this.aggregateId = aggregateId;
        this.version = version;
    }
}
