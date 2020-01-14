package vn.vlong.booklibrary.api.user.command.handler;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import vn.vlong.booklibrary.api.shared.domain.event.Event;
import vn.vlong.booklibrary.api.shared.eventbus.EventSourcePublisher;
import vn.vlong.booklibrary.api.shared.handler.ICommandHandler;
import vn.vlong.booklibrary.api.user.command.domain.command.CreateUserCommand;
import vn.vlong.booklibrary.api.user.command.domain.entity.User;
import vn.vlong.booklibrary.api.user.command.domain.event.UserCreatedEvent;
import vn.vlong.booklibrary.api.user.command.domain.valueobject.Email;
import vn.vlong.booklibrary.api.user.command.domain.valueobject.FullName;
import vn.vlong.booklibrary.api.user.command.domain.valueobject.Password;
import vn.vlong.booklibrary.api.user.command.repository.IUserCommandRepository;
import vn.vlong.booklibrary.api.user.exception.UserNeedCreateIsExist;

@Component
public class CreateUserCommandHandler implements ICommandHandler<CreateUserCommand> {
    private IUserCommandRepository userCommandRepository;
    private EventSourcePublisher<Event> eventEventSourcePublisher;

    public CreateUserCommandHandler(final IUserCommandRepository userCommandRepository,
                                    final EventSourcePublisher<Event> eventEventSourcePublisher) {
        this.userCommandRepository = userCommandRepository;
        this.eventEventSourcePublisher = eventEventSourcePublisher;
    }

    @EventListener
    @Override
    public void handle(CreateUserCommand command) throws Exception {
        if (userCommandRepository.findByEmail(new Email(command.getEmail())).isPresent()) {
            throw new UserNeedCreateIsExist(String.format("Email %s is exist", command.getEmail()));
        }

        User user = User.create(new FullName(command.getFirstName(), command.getLastName()),
                new Email(command.getEmail()), new Password(command.getPassword()));

        userCommandRepository.saveAndFlush(user);
        eventEventSourcePublisher.publish(new UserCreatedEvent(this, user.getUserId().getId(), user.getVersion(),
                user.getFullName().getFirstName(), user.getFullName().getLastName(), user.getEmail().getEmail(),
                user.getPassword().getPassword(), user.isActive(), user.getActiveCode().getActiveCode(),
                user.getUserRole().getRole().getValue()
        ));
    }
}
