package vn.vlong.booklibrary.api.user.command.domain.valueobject;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class EmailTest {

  @Test
  void test_WhenEmailAddressIsNull_ShouldThrowException() {
    assertThrows(IllegalArgumentException.class, () -> {
      new Email(null);
    });
  }

  @Test
  void test_WhenEmailAddressIsEmpty_ShouldThrowException() {
    assertThrows(IllegalArgumentException.class, () -> {
      new Email("");
    });
  }

  @Test
  void test_WhenEmailAddressIsNotValid_ShouldThrowException() {
    assertThrows(IllegalArgumentException.class, () -> {
      new Email("email_not_valid");
    });
  }
}