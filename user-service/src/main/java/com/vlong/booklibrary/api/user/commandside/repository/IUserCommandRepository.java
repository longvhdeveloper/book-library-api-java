package com.vlong.booklibrary.api.user.commandside.repository;

import com.vlong.booklibrary.api.user.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IUserCommandRepository extends CrudRepository<User, UUID> {
    @Query("SELECT u FROM user u WHERE u.email = :email")
    Optional<User> findByEmail(String email);
}
