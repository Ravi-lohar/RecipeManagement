package com.example.RecipeManagement.service;

import com.example.RecipeManagement.model.Comment;
import com.example.RecipeManagement.model.Recipe;
import com.example.RecipeManagement.model.User;
import com.example.RecipeManagement.repository.IRecipeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {
    @Autowired
    IRecipeRepo recipeRepo ;

    public String craeteRecipe(Recipe recipe) {
        recipeRepo.save(recipe);
        return "Recipe added Successfully" ;
    }

    public String removeRecipe(Long recipeId, User user) {
        Recipe recipe = recipeRepo.findById(recipeId).orElse(null);
        if(recipe != null  && recipe.getRecipeOwner().equals(user)){
            recipeRepo.deleteById(recipeId);
            return "Removed Successfully" ;
        }
        else if (recipe == null) {
            return  "Recipe does not exist " ;
        }
        else {
            return "Un-Authorized delete detected....Not allowed";
        }
    }

    public String updateRecipe(Recipe recipe, User user) {
        Recipe recipe1 = recipeRepo.findById(recipe.getRecipeId()).orElse(null);
        if(recipe1 != null  && recipe1.getRecipeOwner().equals(user)){
            recipe1.setRecipeName(recipe.getRecipeName());
            recipe1.setCuisine(recipe.getCuisine());
            recipe1.setIngredients(recipe.getIngredients());
            recipe1.setInstruction(recipe.getInstruction());
            recipe1.setServings(recipe.getServings());
            recipe1.setTotal_time_in_min(recipe.getTotal_time_in_min());
            recipeRepo.save(recipe1);
            return "Updated Successfully Successfully" ;
        }
        else if (recipe1 == null) {
            return  "Recipe does not exist " ;
        }
        else {
            return "Un-Authorized delete detected....Not allowed";
        }

    }


    public boolean validateRecipe(Recipe recipe) {
        return (recipe != null && recipeRepo.existsById(recipe.getRecipeId()));
    }

    public List<Recipe> getAllRecipes() {
        return recipeRepo.findAll();
    }
}
