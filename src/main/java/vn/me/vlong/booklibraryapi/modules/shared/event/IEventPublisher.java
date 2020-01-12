package vn.me.vlong.booklibraryapi.modules.shared.event;

import vn.me.vlong.booklibraryapi.modules.shared.model.event.Event;

public interface IEventPublisher {
    void publish(Event event);
}
