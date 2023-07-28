package com.example.RecipeManagement.controller;

import com.example.RecipeManagement.model.Comment;
import com.example.RecipeManagement.model.Recipe;
import com.example.RecipeManagement.model.User;
import com.example.RecipeManagement.model.dto.SignInInput;
import com.example.RecipeManagement.model.dto.SignUpOutput;
import com.example.RecipeManagement.service.AuthenticationService;
import com.example.RecipeManagement.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
public class UserController {
    @Autowired
    UserService userService ;
    @Autowired
    AuthenticationService authenticationService ;

    @PostMapping("user/signup")
    public SignUpOutput signUpUser(@Valid @RequestBody User user)
    {

        return userService.signUpUser(user);
    }

    @PostMapping("user/signIn")
    public String sigInUser(@RequestBody @Valid SignInInput signInInput)
    {
        return userService.signInUser(signInInput);
    }

    @DeleteMapping("user/signOut")
    public String sigOutUser(@Valid @RequestParam String email,  @RequestParam String token)
    {
        if(authenticationService.authenticate(email,token)) {
            return userService.sigOutUser(email);
        }
        else {
            return "Sign out not allowed for non authenticated user.";
        }

    }


    @PostMapping("recipe")
    public String createRecipe(@Valid @RequestBody Recipe recipe , @Valid @RequestParam String email,  @RequestParam String token)
    {
        if(authenticationService.authenticate(email,token)) {
            return userService.createRecipe(recipe,email);
        }
        else {
            return "Not an Authenticated user activity!!!";
        }
    }

    @DeleteMapping("recipe")
    public String deleteRecipe(@RequestParam Long recipeId, @Valid @RequestParam String email, @RequestParam String token)
    {
        if(authenticationService.authenticate(email,token)) {
            return userService.deleteRecipe(recipeId,email);
        }
        else {
            return "Not an Authenticated user activity!!!";
        }
    }

    @PutMapping("recipe")
    public String updateRecipe(@Valid @RequestBody Recipe recipe , @Valid @RequestParam String email, @RequestParam String token)
    {
        if(authenticationService.authenticate(email,token)) {
            return userService.updateRecipe(recipe,email);
        }
        else {
            return "Not an Authenticated user activity!!!";
        }
    }

    @GetMapping("recipe")
    public List<Recipe> getAllRecipes(@Valid @RequestParam String email, @RequestParam String token){
        if(authenticationService.authenticate(email,token)) {
            return userService.getAllRecipes();
        }
        else {
            return null ;
        }
    }

    @PostMapping("comment")
    public String addComment(@Valid @RequestBody Comment comment, @Valid @RequestParam String commenterEmail, @RequestParam String commenterToken)
    {
        if(authenticationService.authenticate(commenterEmail,commenterToken)) {
            return userService.addComment(comment,commenterEmail);
        }
        else {
            return "Not an Authenticated user activity!!!";
        }
    }


    @DeleteMapping("comment")
    public String removeComment(@RequestParam Long commentId, @Valid @RequestParam String email, @RequestParam String token)
    {
        if(authenticationService.authenticate(email,token)) {
            return userService.removeComment(commentId,email);
        }
        else {
            return "Not an Authenticated user activity!!!";
        }
    }

}
