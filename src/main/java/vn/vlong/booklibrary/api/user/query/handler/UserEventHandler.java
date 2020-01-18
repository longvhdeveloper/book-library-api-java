package vn.vlong.booklibrary.api.user.query.handler;

import org.greenrobot.eventbus.Subscribe;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Subscribe
    public void handle(UserCreatedEvent userCreatedEvent) {
        User user = new User(userCreatedEvent.getAggregateId(), userCreatedEvent.getFirstName(),
                userCreatedEvent.getLastName(), userCreatedEvent.getPassword(),
                userCreatedEvent.isActive(), userCreatedEvent.getActiveCode(), userCreatedEvent.getRole());
        userQueryRepository.save(user);
    }
}
