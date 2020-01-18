package vn.vlong.booklibrary.api.shared.domain.entity;

import vn.vlong.booklibrary.api.shared.domain.event.Event;

import java.lang.reflect.Method;
import java.util.List;

public abstract class BaseEntity<T> implements IEntity<T> {
    private static final String APPLY_METHOD_NAME = "apply";

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

    private Method getApplyMethod(
            Class<? extends BaseEntity> aggregateType,
            Class<? extends Event> eventType
    ) throws NoSuchMethodException {
        Method applyMethod = aggregateType.getDeclaredMethod(APPLY_METHOD_NAME, eventType);
        applyMethod.setAccessible(true);
        return applyMethod;
    }
}
