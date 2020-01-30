package vn.vlong.booklibrary.api.user.command.domain.valueobject;

import java.util.Objects;
import lombok.Getter;
import vn.vlong.booklibrary.api.shared.domain.valueobject.IValueObject;

public class UserRole implements IValueObject<UserRole> {

  @Getter
  private Role role;

  public UserRole(final Role role) {
    if (Objects.isNull(role)) {
      throw new IllegalArgumentException("Role is not valid");
    }
    this.role = role;
  }

  @Override
  public boolean isSameValue(UserRole other) {
    if (Objects.isNull(other)) {
      return false;
    }

    return role.getValue() == other.getRole().getValue();
  }
}
