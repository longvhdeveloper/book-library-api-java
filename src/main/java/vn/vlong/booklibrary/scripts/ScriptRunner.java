package vn.vlong.booklibrary.scripts;

import java.util.concurrent.ExecutionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import vn.vlong.booklibrary.api.eventsource.service.EventSourceService;
import vn.vlong.booklibrary.api.user.command.domain.command.ActiveUserCommand;
import vn.vlong.booklibrary.api.user.command.domain.command.CreateUserCommand;
import vn.vlong.booklibrary.api.user.command.domain.entity.User;
import vn.vlong.booklibrary.api.user.command.domain.valueobject.Role;
import vn.vlong.booklibrary.api.user.command.producer.UserBinderProducer;
import vn.vlong.booklibrary.api.user.exception.ActiveCodeIsNotMatchException;
import vn.vlong.booklibrary.api.user.exception.UserAlreadyActiveException;
import vn.vlong.booklibrary.api.user.exception.UserCreateIsExistException;
import vn.vlong.booklibrary.api.user.exception.UserIsNotFoundException;

@Component
@Slf4j
public class ScriptRunner implements CommandLineRunner {

  @Autowired
  private EventSourceService eventSourceService;

  @Autowired
  private UserBinderProducer userBinderProducer;

  @Override
  public void run(String... args) throws Exception {

  }

  private void addAdmin()
      throws UserCreateIsExistException, ExecutionException, InterruptedException,
      UserIsNotFoundException, ActiveCodeIsNotMatchException, UserAlreadyActiveException {
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
}
