package vn.vlong.booklibrary.api.shared.domain.entity;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import lombok.Getter;
import vn.vlong.booklibrary.api.shared.domain.event.Event;

public abstract class BaseEntity<T> implements IEntity<T> {

  private static final String APPLY_METHOD_NAME = "apply";

  @Getter
  private List<Event> unCommittedEvents;

  public BaseEntity() {
    unCommittedEvents = new LinkedList<>();
  }

  public void apply(List<Event> events) {
    events.forEach(this::apply);
  }

  public void apply(Event event) {
    Class<? extends Event> eventType = event.getClass();
    try {
      getApplyMethod(this.getClass(), eventType).invoke(this, event);
    } catch (ReflectiveOperationException e) {
      throw new RuntimeException(String.format("Aggregate do not know how to apply %s", eventType));
    }
  }

  public void addUnCommittedEvent(Event event) {
    unCommittedEvents.add(event);
  }

  public void clearUnCommittedEvents() {
    unCommittedEvents.clear();
  }

  private Method getApplyMethod(
      Class<? extends BaseEntity> aggregateType,
      Class<? extends Event> eventType
  ) throws NoSuchMethodException {
    Method applyMethod = aggregateType.getDeclaredMethod(APPLY_METHOD_NAME, eventType);
    applyMethod.setAccessible(true);
    return applyMethod;
  }
}
