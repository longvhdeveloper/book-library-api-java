package vn.vlong.booklibrary.api.shared.domain.query;

import org.springframework.context.ApplicationEvent;

public abstract class Query extends ApplicationEvent implements IQuery {
    public Query(Object source) {
        super(source);
    }
}
