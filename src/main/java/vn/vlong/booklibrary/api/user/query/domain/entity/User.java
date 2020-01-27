package vn.vlong.booklibrary.api.user.query.domain.entity;

import javax.persistence.Id;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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

  public User(String email, String firstName, String lastName, String password, boolean isActive,
      String activeCode, int role) {
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
    this.password = password;
    this.isActive = isActive;
    this.activeCode = activeCode;
    this.role = role;
  }

  public void activeUser() {
    this.isActive = true;
  }
}
