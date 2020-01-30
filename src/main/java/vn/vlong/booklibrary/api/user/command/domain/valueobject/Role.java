package vn.vlong.booklibrary.api.user.command.domain.valueobject;

import lombok.Getter;

public enum Role {
  USER_ROLE(1, "USER"), ADMIN_ROLE(2, "ADMIN");

  @Getter
  private int value;

  @Getter
  private String name;

  Role(final int value, final String name) {
    this.value = value;
    this.name = name;
  }

  public static Role valueOf(int value) {
    switch (value) {
      case 1:
        return USER_ROLE;
      case 2:
        return ADMIN_ROLE;
    }
    throw new IllegalArgumentException(String.format("Role value %d is not valid", value));
  }
}
