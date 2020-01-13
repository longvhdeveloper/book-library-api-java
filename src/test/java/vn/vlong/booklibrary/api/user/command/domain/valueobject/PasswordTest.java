package vn.vlong.booklibrary.api.user.command.domain.valueobject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class PasswordTest {

    @Test
    void test_WhenPasswordIsNull_ShouldThrowException(){
        assertThrows(IllegalArgumentException.class, () -> {
           new Password(null);
        });
    }

    @Test
    void test_WhenPasswordIsEmpty_ShouldThrowException(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Password("");
        });
    }
}