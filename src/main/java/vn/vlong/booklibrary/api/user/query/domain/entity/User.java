package vn.vlong.booklibrary.api.user.query.domain.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import vn.vlong.booklibrary.api.shared.domain.entity.IEntity;

@Document("users")
@Builder
@Getter
public class User implements IEntity<User> {

    private String id;

    @Field(name = "first_name")
    private String firstName;

    @Field(name = "last_name")
    private String lastName;

    private String email;

    private String password;

    @Field(name = "is_active")
    private boolean isActive;

    @Field(name = "active_code")
    private String activeCode;

    private int role;

    private int version;

    @Override
    public boolean isSameIdentity(User other) {
        return false;
    }
}
