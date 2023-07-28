package com.example.RecipeManagement.repository;

import com.example.RecipeManagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepo extends JpaRepository<User, Long> {
    User findFirstByUserEmail(String newEmail);
}
