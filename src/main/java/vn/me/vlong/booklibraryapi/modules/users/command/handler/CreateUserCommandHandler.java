package vn.me.vlong.booklibraryapi.modules.users.command.handler;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import vn.me.vlong.booklibraryapi.modules.shared.event.EventPublisher;
import vn.me.vlong.booklibraryapi.modules.users.command.exception.UserIsExistException;
import vn.me.vlong.booklibraryapi.modules.users.command.model.command.CreateUserCommand;
import vn.me.vlong.booklibraryapi.modules.users.command.model.entity.User;
import vn.me.vlong.booklibraryapi.modules.users.command.model.event.UserCreatedEvent;
import vn.me.vlong.booklibraryapi.modules.users.command.model.valueobject.EmailAddress;
import vn.me.vlong.booklibraryapi.modules.users.command.model.valueobject.FullName;
import vn.me.vlong.booklibraryapi.modules.users.command.model.valueobject.Password;
import vn.me.vlong.booklibraryapi.modules.users.command.repository.IUserCommandRepository;

@Component
public class CreateUserCommandHandler {

    private IUserCommandRepository userCommandRepository;
    private EventPublisher eventPublisher;

    public CreateUserCommandHandler(IUserCommandRepository useCommandRepository, EventPublisher eventPublisher) {
        this.userCommandRepository = useCommandRepository;
        this.eventPublisher = eventPublisher;
    }

    @EventListener
    public void handle(CreateUserCommand command) throws Exception {
        if (userCommandRepository.findByEmail(command.getEmail()).isPresent()) {
            throw new UserIsExistException(String.format("User with email %s is exist", command.getEmail()));
        }

        User user = User.create(new EmailAddress(command.getEmail()), new FullName(command.getFirstName(),
                        command.getLastName())
                , new Password(command.getPassword()));
        userCommandRepository.saveAndFlush(user);
        eventPublisher.publish(new UserCreatedEvent(
                this,
                user.getId(),
                user.getEmailAddress().getEmail(),
                user.getFullName().getFirstName(),
                user.getFullName().getLastName(),
                user.getPassword().getPassword(),
                user.isActive(),
                user.getActiveCode().getActiveCode()
        ));
    }
}
