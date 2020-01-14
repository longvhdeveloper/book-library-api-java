package vn.vlong.booklibrary.api.user.query.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import vn.vlong.booklibrary.api.shared.handler.IEventHandler;
import vn.vlong.booklibrary.api.user.command.domain.event.UserCreatedEvent;
import vn.vlong.booklibrary.api.user.query.domain.entity.User;
import vn.vlong.booklibrary.api.user.query.repository.IUserQueryRepository;

@Component
public class UserEventHandler implements IEventHandler {
    private IUserQueryRepository userQueryRepository;

    @Autowired
    public UserEventHandler(IUserQueryRepository userQueryRepository) {
        this.userQueryRepository = userQueryRepository;
    }

    @EventListener
    public void handle(UserCreatedEvent userCreatedEvent) {
        User user = User.builder().id(userCreatedEvent.getAggregateId().toString())
                .firstName(userCreatedEvent.getFirstName())
                .lastName(userCreatedEvent.getLastName())
                .email(userCreatedEvent.getEmail())
                .password(userCreatedEvent.getPassword())
                .isActive(userCreatedEvent.isActive())
                .activeCode(userCreatedEvent.getActiveCode())
                .role(userCreatedEvent.getRole())
                .version(userCreatedEvent.getVersion())
                .build();
        userQueryRepository.save(user);
    }
}
