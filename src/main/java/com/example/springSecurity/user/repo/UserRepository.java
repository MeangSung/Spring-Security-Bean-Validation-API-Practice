package com.example.springSecurity.user.repo;

import com.example.springSecurity.user.repo.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String username);

    Optional<User> findByNickname(String nickname);

}
