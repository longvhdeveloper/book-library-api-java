package vn.vlong.booklibrary.api.shared.bus.event;

import vn.vlong.booklibrary.api.shared.domain.event.Event;

public interface IEventBus {
    void publish(Event event);
}
