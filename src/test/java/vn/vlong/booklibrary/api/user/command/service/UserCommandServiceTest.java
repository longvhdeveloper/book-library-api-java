package vn.vlong.booklibrary.api.user.command.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import vn.vlong.booklibrary.api.eventsource.service.EventSourceService;
import vn.vlong.booklibrary.api.shared.domain.event.Event;
import vn.vlong.booklibrary.api.user.command.controller.request.ActiveUserRequest;
import vn.vlong.booklibrary.api.user.command.controller.request.CreateUserRequest;
import vn.vlong.booklibrary.api.user.command.domain.entity.User;
import vn.vlong.booklibrary.api.user.command.domain.event.UserCreatedEvent;
import vn.vlong.booklibrary.api.user.command.domain.valueobject.Role;
import vn.vlong.booklibrary.api.user.command.producer.UserBinderProducer;
import vn.vlong.booklibrary.api.user.exception.ActiveCodeIsNotMatchException;
import vn.vlong.booklibrary.api.user.exception.UserAlreadyActiveException;
import vn.vlong.booklibrary.api.user.exception.UserCreateIsExistException;
import vn.vlong.booklibrary.api.user.exception.UserIsNotFoundException;


@ExtendWith(MockitoExtension.class)
class UserCommandServiceTest {

  @Mock
  private EventSourceService eventSourceService;
  @Mock
  private UserBinderProducer userBinderProducer;
  @InjectMocks
  private UserCommandService userCommandService;

  @Test
  void test_CreateUser_WhenUserCreateIsExist_ShouldThrowException() {
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    UserCreatedEvent userCreatedEvent = new UserCreatedEvent(
        "jackiehandsome@example.com",
        User.getStream(),
        1,
        "Jackie",
        "Handsome",
        passwordEncoder.encode("password"),
        false,
        passwordEncoder.encode("jackiehandsome@example.com"),
        Role.USER_ROLE.getValue()
    );

    List<Event> events = new LinkedList<>();
    events.add(userCreatedEvent);

    when(eventSourceService.loadEvents(anyString(), anyString())).thenReturn(events);

    assertThrows(UserCreateIsExistException.class, () -> {
      userCommandService.createUser(CreateUserRequest.builder()
          .email("jackiehandsome@example.com")
          .firstName("Jackie")
          .lastName("Handsome")
          .password("password")
          .build());
    });
  }

  @Test
  void test_ActiveUser_WhenActiveUserIsNotExist_ShouldThrowException() {
    when(eventSourceService.loadEvents(anyString(), anyString())).thenReturn(new LinkedList<>());

    assertThrows(UserIsNotFoundException.class, () -> {
      userCommandService.activeUser(new ActiveUserRequest(
          "jackiehandsome@example.com",
          "$2a$10$M/mBuqqK0S22QwaiZYjZKOf3o3DxSOnTVbQEV1IRB1iJyPBnt9zBe"
      ));
    });
  }

  @Test
  void test_ActiveUser_WhenUserIsAlreadyActivated_ShouldThrowException() {
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    UserCreatedEvent userCreatedEvent = new UserCreatedEvent(
        "jackiehandsome@example.com",
        User.getStream(),
        1,
        "Jackie",
        "Handsome",
        passwordEncoder.encode("password"),
        true,
        passwordEncoder.encode("jackiehandsome@example.com"),
        Role.USER_ROLE.getValue()
    );

    List<Event> events = new LinkedList<>();
    events.add(userCreatedEvent);

    when(eventSourceService.loadEvents(anyString(), anyString())).thenReturn(events);

    assertThrows(UserAlreadyActiveException.class, () -> {
      userCommandService.activeUser(new ActiveUserRequest(
          "jackiehandsome@example.com",
          "$2a$10$M/mBuqqK0S22QwaiZYjZKOf3o3DxSOnTVbQEV1IRB1iJyPBnt9zBe"
      ));
    });
  }

  @Test
  void test_ActiveUser_WhenActiveIsNotMatch_ShouldThrowException() {
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    UserCreatedEvent userCreatedEvent = new UserCreatedEvent(
        "jackiehandsome@example.com",
        User.getStream(),
        1,
        "Jackie",
        "Handsome",
        passwordEncoder.encode("password"),
        false,
        "$2a$10$M/mBuqqK0S22QwaiZYjZKOf3o3DxSOnTVbQEV1IRB1iJyPBnt9zBe",
        Role.USER_ROLE.getValue()
    );

    List<Event> events = new LinkedList<>();
    events.add(userCreatedEvent);

    when(eventSourceService.loadEvents(anyString(), anyString())).thenReturn(events);

    assertThrows(ActiveCodeIsNotMatchException.class, () -> {
      userCommandService.activeUser(new ActiveUserRequest("jackiehandsome@example.com",
          "$2a$10$M/mBuqqK0S22QwaiZYjZKOf3o3DxSOnTVbQEV1IRB1iJyPBnt9zeB"));
    });
  }
}