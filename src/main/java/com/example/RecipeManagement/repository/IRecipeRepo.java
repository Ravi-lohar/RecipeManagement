package com.example.RecipeManagement.repository;

import com.example.RecipeManagement.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRecipeRepo extends JpaRepository<Recipe , Long> {
}
