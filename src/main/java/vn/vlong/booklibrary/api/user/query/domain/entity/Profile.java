package vn.vlong.booklibrary.api.user.query.domain.entity;

import java.util.Date;
import javax.persistence.Id;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
@Getter
public class Profile {

  @Id
  private String id;

  private String email;

  private int gender;

  private String address;

  @Field(name = "phone_number")
  private String phoneNumber;

  @Field(name = "birth_day")
  private Date birthDay;

  private String avatar;

  private int job;

  public Profile(String email, int gender,
      String address, String phoneNumber, Date birthDay, String avatar, int job) {
    this.email = email;
    this.gender = gender;
    this.address = address;
    this.phoneNumber = phoneNumber;
    this.birthDay = birthDay;
    this.avatar = avatar;
    this.job = job;
  }
}
