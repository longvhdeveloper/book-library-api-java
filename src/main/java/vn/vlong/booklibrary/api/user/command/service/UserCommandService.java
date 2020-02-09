package vn.vlong.booklibrary.api.user.command.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import vn.vlong.booklibrary.api.eventsource.service.EventSourceService;
import vn.vlong.booklibrary.api.shared.domain.event.Event;
import vn.vlong.booklibrary.api.shared.logger.LogExecutionTime;
import vn.vlong.booklibrary.api.user.command.controller.request.ActiveUserRequest;
import vn.vlong.booklibrary.api.user.command.controller.request.CreateUserRequest;
import vn.vlong.booklibrary.api.user.command.domain.command.ActiveUserCommand;
import vn.vlong.booklibrary.api.user.command.domain.command.CreateUserCommand;
import vn.vlong.booklibrary.api.user.command.domain.entity.User;
import vn.vlong.booklibrary.api.user.command.domain.valueobject.Role;
import vn.vlong.booklibrary.api.user.command.producer.UserBinderProducer;
import vn.vlong.booklibrary.api.user.exception.ActiveCodeIsNotMatchException;
import vn.vlong.booklibrary.api.user.exception.UserAlreadyActiveException;
import vn.vlong.booklibrary.api.user.exception.UserCreateIsExistException;
import vn.vlong.booklibrary.api.user.exception.UserIsNotFoundException;

@Service
public class UserCommandService {

  private EventSourceService eventSourceService;
  private UserBinderProducer userBinderProducer;

  @Autowired
  public UserCommandService(EventSourceService eventSourceService,
      UserBinderProducer userBinderProducer) {
    this.eventSourceService = eventSourceService;
    this.userBinderProducer = userBinderProducer;
  }

  @Async("asyncExecutor")
  @LogExecutionTime
  public CompletableFuture<User> createUser(CreateUserRequest request)
      throws UserCreateIsExistException {
    List<Event> userEvents = eventSourceService.loadEvents(request.getEmail(), User.getStream());
    if (!userEvents.isEmpty()) {
      throw new UserCreateIsExistException(
          String.format("User with email %s is exist", request.getEmail()));
    }

    CreateUserCommand createUserCommand = new CreateUserCommand(
        request.getEmail(),
        request.getFirstName(),
        request.getLastName(),
        request.getPassword(),
        Role.USER_ROLE.getValue()
    );

    User user = new User(createUserCommand);
    eventSourceService.storeEvents(user.getUnCommittedEvents());
    userBinderProducer.send(user);

    return CompletableFuture.completedFuture(user);
  }

  @Async("asyncExecutor")
  @LogExecutionTime
  public CompletableFuture<User> activeUser(ActiveUserRequest request)
      throws ActiveCodeIsNotMatchException, UserAlreadyActiveException, UserIsNotFoundException {
    List<Event> userEvents = eventSourceService.loadEvents(request.getEmail(), User.getStream());

    if (userEvents.isEmpty()) {
      throw new UserIsNotFoundException(
          String.format("User with email %s is not exist", request.getEmail()));
    }
    User user = new User(userEvents);

    ActiveUserCommand activeUserCommand = new ActiveUserCommand(request.getEmail(),
        request.getActiveCode());

    user.handle(activeUserCommand);
    eventSourceService.storeEvents(user.getUnCommittedEvents());
    userBinderProducer.send(user);

    return CompletableFuture.completedFuture(user);
  }
}
