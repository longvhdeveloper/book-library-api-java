package vn.vlong.booklibrary.api.user.command.domain.valueobject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
class UserIdTest {

    @Test
    void test_WhenUUIDIsNull_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new UserId(null, 1);
        });
    }

    @Test
    void test_WhenVersionIsLessThan1_ShouldReturnVersionIs1() {
        UserId userId = new UserId(UUID.randomUUID(), 0);
        assertThat(userId.getVersion(), is(1));
    }
}