package com.example.springSecurity.user.repo;

import com.example.springSecurity.user.repo.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u "
            + "where u.nickname = :username")
    Optional<User> findByUsername(@Param("username")String username);
}
