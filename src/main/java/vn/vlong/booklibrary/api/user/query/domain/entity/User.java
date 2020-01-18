package vn.vlong.booklibrary.api.user.query.domain.entity;

import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;

@Document("users")
@Getter
public class User {

    @Id
    private String id;

    private String email;

    @Field(name = "first_name")
    private String firstName;

    @Field(name = "last_name")
    private String lastName;

    private String password;

    @Field(name = "is_active")
    private boolean isActive;

    @Field(name = "active_code")
    private String activeCode;

    private int role;

    public User(String id, String firstName, String lastName, String password, boolean isActive,
                String activeCode, int role) {
        this.email = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.isActive = isActive;
        this.activeCode = activeCode;
        this.role = role;
    }
}
