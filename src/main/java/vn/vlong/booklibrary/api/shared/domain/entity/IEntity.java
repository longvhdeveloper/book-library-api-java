package vn.vlong.booklibrary.api.shared.domain.entity;

public interface IEntity<T> {
    boolean isSameIdentity(T other);
}
