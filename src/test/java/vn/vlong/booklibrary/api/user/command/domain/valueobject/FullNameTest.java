package vn.vlong.booklibrary.api.user.command.domain.valueobject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class FullNameTest {

    @Test
    void test_WhenFirstNameIsNull__ShouldThrowException(){
        assertThrows(IllegalArgumentException.class, () -> {
            new FullName(null, "Last name");
        });
    }

    @Test
    void test_WhenFirstNameIsEmpty__ShouldThrowException(){
        assertThrows(IllegalArgumentException.class, () -> {
            new FullName("", "Last name");
        });
    }

    @Test
    void test_WhenLastNameIsNull__ShouldThrowException(){
        assertThrows(IllegalArgumentException.class, () -> {
            new FullName("First name", null);
        });
    }

    @Test
    void test_WhenLastNameIsEmpty__ShouldThrowException(){
        assertThrows(IllegalArgumentException.class, () -> {
            new FullName("First name", "");
        });
    }
}