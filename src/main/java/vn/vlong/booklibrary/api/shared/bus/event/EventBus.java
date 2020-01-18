package vn.vlong.booklibrary.api.shared.bus.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.vlong.booklibrary.api.shared.bus.Bus;
import vn.vlong.booklibrary.api.shared.domain.event.Event;

@Component
public class EventBus implements IEventBus {

    private Bus bus;

    @Autowired
    public EventBus(Bus bus) {
        this.bus = bus;
    }

    @Override
    public void publish(Event event) {
        bus.post(event);
    }
}
