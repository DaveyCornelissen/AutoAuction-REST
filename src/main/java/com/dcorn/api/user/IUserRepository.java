package com.dcorn.api.user;

import com.dcorn.api.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    Optional<User> findByIdAndEmail(int id, String email);
}
