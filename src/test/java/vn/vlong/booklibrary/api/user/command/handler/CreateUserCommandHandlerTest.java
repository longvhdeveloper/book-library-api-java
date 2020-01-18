package vn.vlong.booklibrary.api.user.command.handler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.vlong.booklibrary.api.eventsource.service.EventSourceService;
import vn.vlong.booklibrary.api.shared.domain.event.Event;
import vn.vlong.booklibrary.api.user.command.domain.command.CreateUserCommand;
import vn.vlong.booklibrary.api.user.command.domain.event.UserCreatedEvent;
import vn.vlong.booklibrary.api.user.command.domain.valueobject.Role;
import vn.vlong.booklibrary.api.user.exception.UserCreateIsExist;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateUserCommandHandlerTest {

    @Mock
    private EventSourceService eventSourceService;

    @InjectMocks
    private CreateUserCommandHandler createUserCommandHandler;

    @Test
    void test_WhenEmailOfUserNeedCreateIsExist_ShouldThrowException() {
        UserCreatedEvent userCreatedEvent = new UserCreatedEvent("user", 1, "First name",
                "Last name", "vohoanglong07@gmail.com", "123456", true, "" +
                "active code", Role.USER_ROLE.getValue());
        List<Event> events = new LinkedList<>();
        events.add(userCreatedEvent);
        when(eventSourceService.loadEvents(anyString(), anyString())).thenReturn(events);
        CreateUserCommand createUserCommand = new CreateUserCommand("example@example.com",
                "first name", "last name", "123456");
        assertThrows(UserCreateIsExist.class, () -> {
            createUserCommandHandler.handle(createUserCommand);
        });
    }
}