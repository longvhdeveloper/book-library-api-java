package vn.vlong.booklibrary.api.user.command.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.vlong.booklibrary.api.user.command.domain.entity.User;
import vn.vlong.booklibrary.api.user.command.domain.valueobject.Email;
import vn.vlong.booklibrary.api.user.command.domain.valueobject.UserId;

import java.util.Optional;

@Repository
public interface IUserCommandRepository extends JpaRepository<User, UserId> {

    @Query("SELECT u FROM User u WHERE email = ?1")
    Optional<User> findByEmail(Email email);
}
