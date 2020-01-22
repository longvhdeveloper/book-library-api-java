package vn.vlong.booklibrary.api.user.command.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import vn.vlong.booklibrary.api.eventsource.service.EventSourceService;
import vn.vlong.booklibrary.api.shared.domain.event.Event;
import vn.vlong.booklibrary.api.user.command.controller.request.CreateUserRequest;
import vn.vlong.booklibrary.api.user.command.domain.entity.User;
import vn.vlong.booklibrary.api.user.command.domain.event.UserCreatedEvent;
import vn.vlong.booklibrary.api.user.command.domain.valueobject.Role;
import vn.vlong.booklibrary.api.user.command.producer.UserBinderProducer;
import vn.vlong.booklibrary.api.user.exception.UserCreateIsExistException;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserCommandServiceTest {
    @Mock
    private EventSourceService eventSourceService;

    @Mock
    private UserBinderProducer userBinderProducer;

    @InjectMocks
    private UserCommandService userCommandService;

    @Test
    void test_WhenUserCreateIsExist_ShouldThrowException() {
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
}