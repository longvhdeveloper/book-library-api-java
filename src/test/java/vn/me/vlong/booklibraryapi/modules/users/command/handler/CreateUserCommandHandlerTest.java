package vn.me.vlong.booklibraryapi.modules.users.command.handler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.me.vlong.booklibraryapi.modules.users.command.exception.UserIsExistException;
import vn.me.vlong.booklibraryapi.modules.users.command.model.command.CreateUserCommand;
import vn.me.vlong.booklibraryapi.modules.users.command.model.entity.User;
import vn.me.vlong.booklibraryapi.modules.users.command.model.valueobject.*;
import vn.me.vlong.booklibraryapi.modules.users.command.repository.IUserCommandRepository;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(value = {MockitoExtension.class})
class CreateUserCommandHandlerTest {
    @Mock
    private IUserCommandRepository useCommandRepository;

    @InjectMocks
    private CreateUserCommandHandler createUserCommandHandler;

    @Test
    void test_WhenEmailExists_ShouldThrowException() {
        CreateUserCommand createUserCommand = new CreateUserCommand(this, "example@example.com", "first name",
                "last name", "123456");
        User user = new User(UUID.randomUUID(), new EmailAddress(createUserCommand.getEmail()),
                new FullName(createUserCommand.getFirstName(), createUserCommand.getLastName()),
                new Password(createUserCommand.getPassword()), "0123456", new Date(), Gender.UNKNOWN, false,
                new ActiveCode(createUserCommand.getEmail()), UserRole.USER_ROLE);

        when(useCommandRepository.findByEmail(createUserCommand.getEmail())).thenReturn(Optional.of(user));

        assertThrows(UserIsExistException.class, () -> {
            createUserCommandHandler.handle(createUserCommand);
        });
    }
}