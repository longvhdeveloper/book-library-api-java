package vn.me.vlong.booklibraryapi.modules.shared.model.valueobject;

public interface IValueObject<T extends IValueObject<T>> {
    boolean sameValueAs(T other);
}
