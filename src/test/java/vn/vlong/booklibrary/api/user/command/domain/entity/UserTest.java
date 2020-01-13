package vn.vlong.booklibrary.api.user.command.domain.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import vn.vlong.booklibrary.api.user.command.domain.valueobject.Email;
import vn.vlong.booklibrary.api.user.command.domain.valueobject.FullName;
import vn.vlong.booklibrary.api.user.command.domain.valueobject.Password;
import vn.vlong.booklibrary.api.user.command.domain.valueobject.Role;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
class UserTest {

    @Test
    void test_CreateUser_WhenFullNameIsNull_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            User.create(null, new Email("example@example.com"), new Password("123456"));
        });
    }

    @Test
    void test_CreateUser_WhenEmailIsNull_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            User.create(new FullName("first name", "last name"), null, new Password(
                    "123456"));
        });
    }

    @Test
    void test_CreateUser_WhenPasswordIsNull_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            User.create(new FullName("first name", "last name"), new Email("example@example.com"), null);
        });
    }

    @Test
    void test_CreateUser_WhenValidData_ShouldReturnUserNotActiveAndUserRole() {
        User user = User.create(new FullName("first name", "last name"), new Email("example@example.com"),
                new Password("123456"));

        assertThat(user.getFullName().getFirstName(), is("first name"));
        assertThat(user.getFullName().getLastName(), is("last name"));
        assertThat(user.getEmail().getEmail(), is("example@example.com"));
        assertThat(user.isActive(), is(false));
        assertThat(user.getUserRole().getRole(), is(Role.USER_ROLE));
    }
}