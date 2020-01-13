package vn.vlong.booklibrary.api.shared.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import vn.vlong.booklibrary.api.shared.domain.command.Command;

@Component
public class CommandGateway<T extends Command> implements ICommandGateway<T> {
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public CommandGateway(final ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void send(T command) {
        applicationEventPublisher.publishEvent(command);
    }
}
