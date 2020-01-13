package vn.vlong.booklibrary.api.user.command.domain.valueobject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class EmailTest {
    @Test
    void test_WhenEmailAddressIsNull_ShouldThrowException(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Email(null);
        });
    }

    @Test
    void test_WhenEmailAddressIsEmpty_ShouldThrowException(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Email("");
        });
    }

    @Test
    void test_WhenEmailAddressIsNotValid_ShouldThrowException(){
        assertThrows(IllegalArgumentException.class, () -> {
            new Email("email_not_valid");
        });
    }
}