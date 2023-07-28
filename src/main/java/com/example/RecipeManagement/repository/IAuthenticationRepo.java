package com.example.RecipeManagement.repository;

import com.example.RecipeManagement.model.AuthenticationToken;
import com.example.RecipeManagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAuthenticationRepo extends JpaRepository<AuthenticationToken , Long> {
    AuthenticationToken findFirstByTokenValue(String authTokenValue);

    AuthenticationToken findFirstByUser(User user);
}
