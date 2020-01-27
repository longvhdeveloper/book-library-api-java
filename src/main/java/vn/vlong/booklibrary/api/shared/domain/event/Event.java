package vn.vlong.booklibrary.api.shared.domain.event;

import lombok.Getter;

@Getter
public abstract class Event implements IEvent {

  protected String aggregateId;
  protected int version;
  protected String stream;
}
