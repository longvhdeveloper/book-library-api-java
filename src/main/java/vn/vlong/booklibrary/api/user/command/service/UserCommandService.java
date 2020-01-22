package vn.vlong.booklibrary.api.user.command.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import vn.vlong.booklibrary.api.eventsource.service.EventSourceService;
import vn.vlong.booklibrary.api.shared.domain.event.Event;
import vn.vlong.booklibrary.api.shared.logger.LogExecutionTime;
import vn.vlong.booklibrary.api.user.command.controller.request.CreateUserRequest;
import vn.vlong.booklibrary.api.user.command.domain.command.CreateUserCommand;
import vn.vlong.booklibrary.api.user.command.domain.entity.User;
import vn.vlong.booklibrary.api.user.command.producer.UserBinderProducer;
import vn.vlong.booklibrary.api.user.exception.UserCreateIsExistException;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class UserCommandService {
    private EventSourceService eventSourceService;
    private UserBinderProducer userBinderProducer;

    @Autowired
    public UserCommandService(EventSourceService eventSourceService, UserBinderProducer userBinderProducer) {
        this.eventSourceService = eventSourceService;
        this.userBinderProducer = userBinderProducer;
    }

    @Async("asyncExecutor")
    @LogExecutionTime
    public CompletableFuture<Mono<User>> createUser(CreateUserRequest request) throws UserCreateIsExistException {
        // TODO logic check user is exist
        List<Event> userEvents = eventSourceService.loadEvents(request.getEmail(), User.getStream());
        if (!userEvents.isEmpty()) {
            throw new UserCreateIsExistException(String.format("User with email %s is exist", request.getEmail()));
        }

        CreateUserCommand createUserCommand = new CreateUserCommand(
                request.getEmail(),
                request.getFirstName(),
                request.getLastName(),
                request.getPassword()
        );

        User user = new User(createUserCommand);
        eventSourceService.storeEvents(user.getUnCommittedEvents());
        userBinderProducer.send(user);

        return CompletableFuture.completedFuture(Mono.just(user));
    }
}
