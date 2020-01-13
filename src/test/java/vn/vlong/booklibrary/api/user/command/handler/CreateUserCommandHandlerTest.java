package vn.vlong.booklibrary.api.user.command.handler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.vlong.booklibrary.api.user.command.domain.command.CreateUserCommand;
import vn.vlong.booklibrary.api.user.command.domain.entity.User;
import vn.vlong.booklibrary.api.user.command.domain.valueobject.Email;
import vn.vlong.booklibrary.api.user.command.domain.valueobject.FullName;
import vn.vlong.booklibrary.api.user.command.domain.valueobject.Password;
import vn.vlong.booklibrary.api.user.command.repository.IUserCommandRepository;
import vn.vlong.booklibrary.api.user.exception.UserNeedCreateIsExist;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateUserCommandHandlerTest {

    @Mock
    private IUserCommandRepository userCommandRepository;

    @InjectMocks
    private CreateUserCommandHandler createUserCommandHandler;

    @Test
    void test_WhenEmailOfUserNeedCreateIsExist_ShouldThrowException() {
        when(userCommandRepository.findByEmail(any(Email.class))).thenReturn(Optional.of(User.create(
                new FullName("first name", "last name"),
                new Email("example@example.com"),
                new Password("123456")
        )));
        CreateUserCommand createUserCommand = new CreateUserCommand(this, "example@example.com",
                "first name", "last name", "123456");
        assertThrows(UserNeedCreateIsExist.class, () -> {
            createUserCommandHandler.handle(createUserCommand);
        });
    }
}