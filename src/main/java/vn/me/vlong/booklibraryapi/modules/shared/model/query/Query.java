package vn.me.vlong.booklibraryapi.modules.shared.model.query;

import org.springframework.context.ApplicationEvent;

public abstract class Query extends ApplicationEvent implements IQuery {

    public Query(Object source) {
        super(source);
    }
}
