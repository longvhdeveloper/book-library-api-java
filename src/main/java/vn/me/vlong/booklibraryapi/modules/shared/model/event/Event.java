package vn.me.vlong.booklibraryapi.modules.shared.model.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;

@Getter
public abstract class Event extends ApplicationEvent implements IEvent {
    private UUID aggregateId;

    public Event(Object source, UUID aggregateId) {
        super(source);
        this.aggregateId = aggregateId;
    }
}
