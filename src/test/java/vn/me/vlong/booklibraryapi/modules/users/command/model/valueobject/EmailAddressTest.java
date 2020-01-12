package vn.me.vlong.booklibraryapi.modules.users.command.model.valueobject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class EmailAddressTest {

    @Test
    void test_WhenEmailAddressIsNull_ShouldThrowException(){
        assertThrows(IllegalArgumentException.class, () -> {
            new EmailAddress(null);
        });
    }

    @Test
    void test_WhenEmailAddressIsEmpty_ShouldThrowException(){
        assertThrows(IllegalArgumentException.class, () -> {
            new EmailAddress("");
        });
    }

    @Test
    void test_WhenEmailAddressIsNotValid_ShouldThrowException(){
        assertThrows(IllegalArgumentException.class, () -> {
            new EmailAddress("email_not_valid");
        });
    }
}