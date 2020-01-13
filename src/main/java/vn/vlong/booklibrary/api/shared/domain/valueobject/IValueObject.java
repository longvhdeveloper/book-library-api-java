package vn.vlong.booklibrary.api.shared.domain.valueobject;

public interface IValueObject<T> {
    boolean isSameValue(T other);
}
