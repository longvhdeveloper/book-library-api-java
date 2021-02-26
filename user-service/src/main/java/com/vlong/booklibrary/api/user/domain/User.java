package com.vlong.booklibrary.api.user.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        })
public class User {

    @Id
    @GeneratedValue
    @Getter
    private UUID id;

    @Column(length = 255)
    @Getter
    private String email;

    @Column(length = 64)
    @Getter
    private String password;

    @Column(unique = true)
    @Getter
    private String activateCode;

    @Column(length = 255)
    @Getter
    private String avatar;

    @Setter
    private int statusCode;

    @Transient
    @Getter
    private Status status;

    @Setter
    private int roleCode;

    @Transient
    @Getter
    private Role role;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date createdDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_date")
    private Date updatedDate;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.role = Role.MEMBER;
        this.status = Status.PENDING;
    }

    @PostLoad
    void fillTransient() {
        if (statusCode > 0) {
            this.status = Status.of(statusCode);
        }

        if (roleCode > 0) {
            this.role = Role.of(roleCode);
        }
    }

    @PrePersist
    void fillPersistent() {
        if (!Objects.isNull(status)) {
            this.statusCode = status.getStatus();
        }

        if (!Objects.isNull(role)) {
            this.roleCode = role.getRole();
        }
    }
}
