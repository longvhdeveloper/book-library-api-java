package vn.vlong.booklibrary.api.user.command.domain.valueobject;

import java.util.Objects;
import javax.persistence.Column;
import lombok.Getter;
import org.springframework.util.StringUtils;
import vn.vlong.booklibrary.api.shared.domain.valueobject.IValueObject;

public class FullName implements IValueObject<FullName> {

  @Getter
  @Column(name = "first_name")
  private String firstName;

  @Getter
  @Column(name = "lastName")
  private String lastName;

  public FullName(String firstName, String lastName) {
    if (StringUtils.isEmpty(firstName)) {
      throw new IllegalArgumentException("First name is not valid");
    }

    if (StringUtils.isEmpty(lastName)) {
      throw new IllegalArgumentException("Last name is not valid");
    }

    this.firstName = firstName;
    this.lastName = lastName;
  }

  @Override
  public boolean isSameValue(FullName other) {
    if (Objects.isNull(other)) {
      return false;
    }

    return this.firstName.equals(other.getFirstName()) && this.lastName.equals(other.getLastName());
  }
}
