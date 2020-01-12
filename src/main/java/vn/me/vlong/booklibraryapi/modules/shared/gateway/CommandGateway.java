package vn.me.vlong.booklibraryapi.modules.shared.gateway;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import vn.me.vlong.booklibraryapi.modules.shared.model.command.Command;

@Component
public class CommandGateway implements ICommandGateway {

    private ApplicationEventPublisher applicationEventPublisher;

    public CommandGateway(final ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void send(Command command) {
        applicationEventPublisher.publishEvent(command);
    }
}
