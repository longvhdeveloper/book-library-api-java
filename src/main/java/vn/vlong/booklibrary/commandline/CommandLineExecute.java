package vn.vlong.booklibrary.commandline;

import com.github.javafaker.Faker;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import vn.vlong.booklibrary.api.eventsource.service.EventSourceService;
import vn.vlong.booklibrary.api.shared.domain.event.Event;
import vn.vlong.booklibrary.api.shared.logger.LogExecutionTime;
import vn.vlong.booklibrary.api.user.command.domain.command.ActiveUserCommand;
import vn.vlong.booklibrary.api.user.command.domain.command.CreateUserCommand;
import vn.vlong.booklibrary.api.user.command.domain.entity.User;
import vn.vlong.booklibrary.api.user.command.domain.valueobject.Role;
import vn.vlong.booklibrary.api.user.command.producer.UserBinderProducer;
import vn.vlong.booklibrary.api.user.exception.ActiveCodeIsNotMatchException;
import vn.vlong.booklibrary.api.user.exception.UserAlreadyActiveException;

@Component
@Slf4j
public class CommandLineExecute implements CommandLineRunner {

  @Autowired
  private EventSourceService eventSourceService;

  @Autowired
  private UserBinderProducer userBinderProducer;

  @Override
  public void run(String... args) throws Exception {
  }

  private void addAdmin()
      throws ActiveCodeIsNotMatchException, UserAlreadyActiveException {
    CreateUserCommand createUserCommand = new CreateUserCommand(
        "admin@myschool.com",
        "Jack",
        "Babel",
        "1q2w3e4r",
        Role.ADMIN_ROLE.getValue()
    );

    User user = new User(createUserCommand);
    eventSourceService.storeEvents(user.getUnCommittedEvents());
    userBinderProducer.send(user);
    log.info("CREATE ADMIN DONE !!!!");
    activeAdmin(user);
  }

  private void activeAdmin(User user)
      throws ActiveCodeIsNotMatchException, UserAlreadyActiveException {
    ActiveUserCommand activeUserCommand = new ActiveUserCommand(user.getEmail().getEmail(),
        user.getActiveCode().getActiveCode());

    user.handle(activeUserCommand);
    eventSourceService.storeEvents(user.getUnCommittedEvents());
    userBinderProducer.send(user);
    log.info("ACTIVE ADMIN DONE !!!!");
  }

  private void generateUser() {
    Faker faker = new Faker();
    IntStream.range(0, 1000).forEach(i -> {
      CreateUserCommand createUserCommand = new CreateUserCommand(
          faker.internet().emailAddress(),
          faker.name().firstName(),
          faker.name().lastName(),
          "123456",
          Role.USER_ROLE.getValue()
      );

      User user = new User(createUserCommand);
      eventSourceService.storeEvents(user.getUnCommittedEvents());
      userBinderProducer.send(user);
      log.info("CREATE USER {} DONE !!!!", i);
    });
  }

  @LogExecutionTime
  private void restoreUsers() {
    // Get distinct event source
    List<Event> events = eventSourceService.loadDistinctAllEvents();
    // Iterator aggregate id list
    events.forEach(event -> {
      // Restore User by aggregate id
      List<Event> eventList = eventSourceService
          .loadEvents(event.getAggregateId(), event.getStream());
      userBinderProducer.send(eventList);
    });

    System.out.println("RESTORE USER DONE !!!");
  }

  @Async("asyncExecutor")
  @LogExecutionTime
  public CompletableFuture<User> restoreUser(String aggregateId) {
    List<Event> userEvents = eventSourceService.loadEvents(aggregateId, User.getStream());

    if (userEvents.isEmpty()) {
      throw new IllegalArgumentException(
          String.format("User with email %s is not exist", aggregateId));
    }

    return CompletableFuture.completedFuture(new User(userEvents));
  }
}
