package vn.vlong.booklibrary.api.user.command.domain.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import vn.vlong.booklibrary.api.shared.domain.event.Event;

@NoArgsConstructor
@Getter
public class UserActivatedEvent extends Event {

  private boolean active;

  public UserActivatedEvent(String aggregateId, String stream, int version, boolean active) {
    this.aggregateId = aggregateId;
    this.stream = stream;
    this.version = version;
    this.active = active;
  }
}
