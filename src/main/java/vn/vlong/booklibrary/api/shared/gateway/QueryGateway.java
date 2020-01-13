package vn.vlong.booklibrary.api.shared.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import vn.vlong.booklibrary.api.shared.domain.query.Query;

@Component
public class QueryGateway<T extends Query> implements IQueryGateway<T> {

    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public QueryGateway(final ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void send(T query) {
        applicationEventPublisher.publishEvent(query);
    }
}
