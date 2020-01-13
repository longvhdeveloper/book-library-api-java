package vn.vlong.booklibrary.api.user.command.domain.valueobject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class UserRoleTest {

    @Test
    void test_WhenRoleIsNull_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
           new UserRole(null);
        });
    }
}