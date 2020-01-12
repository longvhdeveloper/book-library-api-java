package vn.me.vlong.booklibraryapi.modules.users.command.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.me.vlong.booklibraryapi.modules.users.command.model.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface IUserCommandRepository extends JpaRepository<User, UUID> {

    @Query("SELECT u FROM User u WHERE email=?1")
    public Optional<User> findByEmail(String email);
}
