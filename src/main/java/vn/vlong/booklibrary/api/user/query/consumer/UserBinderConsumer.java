package vn.vlong.booklibrary.api.user.query.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import vn.vlong.booklibrary.api.user.channel.UserBinderSink;
import vn.vlong.booklibrary.api.user.exception.UserIsNotFoundException;
import vn.vlong.booklibrary.api.user.query.domain.entity.User;
import vn.vlong.booklibrary.api.user.query.domain.event.UserActivatedEvent;
import vn.vlong.booklibrary.api.user.query.domain.event.UserCreatedEvent;
import vn.vlong.booklibrary.api.user.query.repository.IUserQueryRepository;

@EnableBinding(UserBinderSink.class)
public class UserBinderConsumer {

  private static ObjectMapper objectMapper = new ObjectMapper();
  private IUserQueryRepository userQueryRepository;

  @Autowired
  public UserBinderConsumer(IUserQueryRepository userQueryRepository) {
    this.userQueryRepository = userQueryRepository;
  }

  @StreamListener(UserBinderSink.INPUT)
  public void receive(@Payload String payload, @Header(name = "type") String type) {
    try {
      System.out.println("TYPE:::" + type);
      System.out.println("Payload:::" + payload);
      switch (type) {
        case "UserCreatedEvent":
          UserCreatedEvent userCreatedEvent = objectMapper
              .readValue(payload, UserCreatedEvent.class);
          addNewUser(userCreatedEvent);
          break;
        case "UserActivatedEvent":
          UserActivatedEvent userActivatedEvent = objectMapper
              .readValue(payload, UserActivatedEvent.class);
          activeUser(userActivatedEvent);
          break;

      }
    } catch (Exception e) {
      throw new RuntimeException(String.format("Can not process payload %s. Reason::::%s", payload,
          e.getMessage()));
    }
  }

  private void addNewUser(UserCreatedEvent userCreatedEvent) {
    User user = new User(
        userCreatedEvent.getAggregateId(),
        userCreatedEvent.getFirstName(),
        userCreatedEvent.getLastName(),
        userCreatedEvent.getPassword(),
        userCreatedEvent.isActive(),
        userCreatedEvent.getActiveCode(),
        userCreatedEvent.getRole()
    );

    userQueryRepository.save(user);
  }

  private void activeUser(UserActivatedEvent userActivatedEvent) throws UserIsNotFoundException {
    User user = userQueryRepository.findByEmail(userActivatedEvent.getAggregateId())
        .orElseThrow(() ->
            new UserIsNotFoundException(
                String.format("User with email %s is not found",
                    userActivatedEvent.getAggregateId())));
    user.activeUser();
    userQueryRepository.save(user);
  }
}
