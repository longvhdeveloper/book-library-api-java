package vn.vlong.booklibrary.api.shared.domain.valueobject;

import java.io.Serializable;

public interface IValueObject<T> extends Serializable {

  boolean isSameValue(T other);
}
