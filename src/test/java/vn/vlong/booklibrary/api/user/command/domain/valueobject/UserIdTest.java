package vn.vlong.booklibrary.api.user.command.domain.valueobject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
class UserIdTest {

    @Test
    void test_WhenUUIDIsNull_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new UserId(null);
        });
    }
}