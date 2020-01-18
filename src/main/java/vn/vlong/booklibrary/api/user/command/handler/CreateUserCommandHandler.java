package vn.vlong.booklibrary.api.user.command.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.vlong.booklibrary.api.eventsource.service.EventSourceService;
import vn.vlong.booklibrary.api.shared.bus.event.EventBus;
import vn.vlong.booklibrary.api.shared.domain.event.Event;
import vn.vlong.booklibrary.api.shared.handler.ICommandHandler;
import vn.vlong.booklibrary.api.shared.logger.LogExecutionTime;
import vn.vlong.booklibrary.api.user.command.domain.command.CreateUserCommand;
import vn.vlong.booklibrary.api.user.command.domain.entity.User;
import vn.vlong.booklibrary.api.user.command.domain.event.UserCreatedEvent;
import vn.vlong.booklibrary.api.user.command.domain.valueobject.Email;
import vn.vlong.booklibrary.api.user.command.domain.valueobject.FullName;
import vn.vlong.booklibrary.api.user.command.domain.valueobject.Password;
import vn.vlong.booklibrary.api.user.exception.UserCreateIsExist;

import java.util.List;

@Component
public class CreateUserCommandHandler implements ICommandHandler<CreateUserCommand> {

    private EventSourceService eventSourceService;
    private EventBus eventBus;

    @Autowired
    public CreateUserCommandHandler(EventSourceService eventSourceService, EventBus eventBus) {
        this.eventSourceService = eventSourceService;
        this.eventBus = eventBus;
    }

    @Override
    @LogExecutionTime
    public void handle(CreateUserCommand command) throws Exception {
        List<Event> events = eventSourceService.loadEvents(command.getEmail(), "user");
        if (!events.isEmpty()) {
            throw new UserCreateIsExist(String.format("User with email %s is exist", command.getEmail()));
        }

        User user = User.create(new FullName(command.getFirstName(), command.getLastName()),
                new Email(command.getEmail()), new Password(command.getPassword()));

        UserCreatedEvent userCreatedEvent = new UserCreatedEvent(user.getStream(), user.getVersion(),
                user.getFullName().getFirstName(), user.getFullName().getLastName(),
                user.getEmail().getEmail(), user.getPassword().getPassword(), user.isActive(),
                user.getActiveCode().getActiveCode(), user.getUserRole().getRole().getValue());

        eventSourceService.storeEvent(userCreatedEvent);
        eventBus.publish(userCreatedEvent);
    }
}
