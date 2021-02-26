package com.vlong.booklibrary.api.user.commandside.handler;

import com.vlong.booklibrary.api.user.commandside.command.CreateUserCommand;
import com.vlong.booklibrary.api.user.commandside.repository.IUserCommandRepository;
import com.vlong.booklibrary.api.user.domain.Role;
import com.vlong.booklibrary.api.user.domain.Status;
import com.vlong.booklibrary.api.user.domain.User;
import com.vlong.booklibrary.api.user.exception.UserConflictException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;

@ExtendWith(MockitoExtension.class)
class CreateUserCommandHandlerTest {

    @Mock
    private IUserCommandRepository userCommandRepository;

    private CreateUserCommandHandler createUserCommandHandler;

    @BeforeEach
    public void beforeEach() {
        createUserCommandHandler = new CreateUserCommandHandler(userCommandRepository, new BCryptPasswordEncoder());
    }

    @Test
    public void test_ShouldThrowException_WhenEmailIsExist() {
        CreateUserCommand command = new CreateUserCommand(
                "vohoanglong07@gmail.com",
                "1q2w3e4r",
                "Long",
                "Vo"
        );

        lenient().when(userCommandRepository.findByEmail(command.getEmail())).thenReturn(Optional.of(new User()));
        assertThrows(UserConflictException.class, () -> createUserCommandHandler.handle(command));
        verify(userCommandRepository, times(1)).findByEmail(anyString());
    }

    @Test
    public void test_ShouldSaveUser_WhenUserIsNotConflict() throws UserConflictException {
        CreateUserCommand command = new CreateUserCommand(
                "vohoanglong07@gmail.com",
                "1q2w3e4r",
                "Long",
                "Vo"
        );

        User user = new User(command.getEmail(), command.getPassword());

        lenient().when(userCommandRepository.findByEmail(command.getEmail())).thenReturn(Optional.empty());
        lenient().when(userCommandRepository.save(any())).thenReturn(user);

        User saveUser = createUserCommandHandler.handle(command).orElseGet(null);

        assertThat(saveUser.getEmail(), is(user.getEmail()));

        verify(userCommandRepository, times(1)).findByEmail(anyString());
        verify(userCommandRepository, times(1)).save(any());
    }

    @Test
    public void test_ShouldStatusUserIsPending_WhenUserIsNotConflict() throws UserConflictException {
        CreateUserCommand command = new CreateUserCommand(
                "vohoanglong07@gmail.com",
                "1q2w3e4r",
                "Long",
                "Vo"
        );

        User user = new User(command.getEmail(), command.getPassword());

        lenient().when(userCommandRepository.findByEmail(command.getEmail())).thenReturn(Optional.empty());
        lenient().when(userCommandRepository.save(any())).thenReturn(user);

        User saveUser = createUserCommandHandler.handle(command).orElseGet(null);
        assertThat(saveUser.getStatus(), is(Status.PENDING));
    }

    @Test
    public void test_ShouldRoleUserIsMember_WhenUserIsNotConflict() throws UserConflictException {
        CreateUserCommand command = new CreateUserCommand(
                "vohoanglong07@gmail.com",
                "1q2w3e4r",
                "Long",
                "Vo"
        );

        User user = new User(command.getEmail(), command.getPassword());

        lenient().when(userCommandRepository.findByEmail(command.getEmail())).thenReturn(Optional.empty());
        lenient().when(userCommandRepository.save(any())).thenReturn(user);

        User saveUser = createUserCommandHandler.handle(command).orElseGet(null);
        assertThat(saveUser.getRole(), is(Role.MEMBER));
    }

    @Test
    public void test_ShouldPasswordIsHash_WhenUserIsNotConflict() throws Exception {
        CreateUserCommand command = new CreateUserCommand(
                "vohoanglong07@gmail.com",
                "1q2w3e4r",
                "Long",
                "Vo"
        );

        User user = new User(command.getEmail(), command.getPassword());

        lenient().when(userCommandRepository.findByEmail(command.getEmail())).thenReturn(Optional.empty());
        lenient().when(userCommandRepository.save(any())).thenReturn(new User(command.getEmail(), anyString()));

        User saveUser = createUserCommandHandler.handle(command).orElseGet(null);
        assertThat(saveUser.getPassword(), not(is(command.getPassword())));
    }

}