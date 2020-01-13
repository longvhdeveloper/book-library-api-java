package vn.vlong.booklibrary.api.user.command.domain.valueobject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
class ActiveCodeTest {

    @Test
    void test_WhenDataIsNull_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ActiveCode(null);
        });
    }

    @Test
    void test_WhenDataIsEmpty_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ActiveCode("");
        });
    }
}