package vn.vlong.booklibrary.api.shared.producer;

import java.util.List;
import vn.vlong.booklibrary.api.shared.domain.entity.BaseEntity;
import vn.vlong.booklibrary.api.shared.domain.event.Event;

public interface IProducer {

  void send(Event event);

  void send(List<Event> events);

  void send(BaseEntity entity);
}
