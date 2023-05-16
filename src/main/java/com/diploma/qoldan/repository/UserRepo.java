package com.diploma.qoldan.repository;

import com.diploma.qoldan.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {
    User findByEmail(String email);
}
