package vn.vlong.booklibrary.api.shared.event;

import vn.vlong.booklibrary.api.shared.domain.event.Event;

public interface IEventSourcePublisher<T extends Event> {
    void publish(T event);
}
