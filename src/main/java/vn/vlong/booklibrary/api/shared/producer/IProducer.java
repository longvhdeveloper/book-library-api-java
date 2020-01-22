package vn.vlong.booklibrary.api.shared.producer;

import vn.vlong.booklibrary.api.shared.domain.entity.BaseEntity;
import vn.vlong.booklibrary.api.shared.domain.event.Event;

import java.util.List;

public interface IProducer {
    void send(Event event);

    void send(List<Event> events);

    void send(BaseEntity entity);
}
