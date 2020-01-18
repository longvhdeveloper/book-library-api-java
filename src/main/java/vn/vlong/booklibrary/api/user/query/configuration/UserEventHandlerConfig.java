package vn.vlong.booklibrary.api.user.query.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import vn.vlong.booklibrary.api.shared.bus.Bus;
import vn.vlong.booklibrary.api.user.query.handler.UserEventHandler;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Configuration
public class UserEventHandlerConfig {
    @Autowired
    private Bus bus;

    @Autowired
    private UserEventHandler userEventHandler;

    @PostConstruct
    public void init() {
        bus.register(userEventHandler);
    }

    @PreDestroy
    public void destroy() {
        bus.unregister(userEventHandler);
    }
}
