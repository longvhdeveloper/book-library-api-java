package vn.me.vlong.booklibraryapi.modules.shared.model.entity;

import lombok.Getter;

@Getter
public abstract class BaseEntity<T extends BaseEntity<T>> {
    public boolean sameIdentityAs(final T that) {
        return this.equals(that);
    }
}
