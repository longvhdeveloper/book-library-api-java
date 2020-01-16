package vn.vlong.booklibrary.api.user.query.domain.entity;

import lombok.Getter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import vn.vlong.booklibrary.api.shared.domain.entity.IEntity;

import javax.persistence.Id;

@Document("users")
@Getter
public class User implements IEntity<User> {

    @Id
    private String id;

    @Indexed
    @Field(name = "first_name")
    private String firstName;

    @Indexed
    @Field(name = "last_name")
    private String lastName;

    @Indexed
    private String email;

    private String password;

    @Indexed
    @Field(name = "is_active")
    private boolean isActive;

    @Field(name = "active_code")
    private String activeCode;

    private int role;

    public User(String id, String firstName, String lastName, String email, String password, boolean isActive,
                String activeCode, int role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.isActive = isActive;
        this.activeCode = activeCode;
        this.role = role;
    }

    @Override
    public boolean isSameIdentity(User other) {
        return false;
    }
}
