package vn.vlong.booklibrary.api.user.command.domain.valueobject;

import java.util.Objects;
import lombok.Getter;
import org.springframework.util.StringUtils;
import vn.vlong.booklibrary.api.shared.domain.valueobject.IValueObject;

public class ActiveCode implements IValueObject<ActiveCode> {

  @Getter
  private String activeCode;

  public ActiveCode(final String activeCode) {
    if (StringUtils.isEmpty(activeCode)) {
      throw new IllegalArgumentException("Active code is not valid");
    }
    this.activeCode = activeCode;
  }

  @Override
  public boolean isSameValue(ActiveCode other) {
    if (Objects.isNull(other)) {
      return false;
    }
    return other.getActiveCode().equals(activeCode);
  }
}
