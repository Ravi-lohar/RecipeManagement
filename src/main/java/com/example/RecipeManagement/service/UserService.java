package com.example.RecipeManagement.service;

import com.example.RecipeManagement.model.AuthenticationToken;
import com.example.RecipeManagement.model.Comment;
import com.example.RecipeManagement.model.Recipe;
import com.example.RecipeManagement.model.User;
import com.example.RecipeManagement.model.dto.SignInInput;
import com.example.RecipeManagement.model.dto.SignUpOutput;
import com.example.RecipeManagement.repository.IUserRepo;
import com.example.RecipeManagement.service.emailUtility.EmailHandler;
import com.example.RecipeManagement.service.hashingUtility.PasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    @Autowired
    IUserRepo userRepo ;
    @Autowired
    AuthenticationService authenticationService ;

    @Autowired
    RecipeService recipeService ;

    @Autowired
    CommentService commentService ;

    public SignUpOutput signUpUser(User user) {

        boolean signUpStatus = true;
        String signUpStatusMessage = null;

        String newEmail = user.getUserEmail();

        if(newEmail == null)
        {
            signUpStatusMessage = "Invalid email";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }

        //check if this user email already exists ??
        User existingUser = userRepo.findFirstByUserEmail(newEmail);

        if(existingUser != null)
        {
            signUpStatusMessage = "Email already registered!!!";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }

        //hash the password: encrypt the password
        try {
            String encryptedPassword = PasswordEncryptor.encryptPassword(user.getUserPassword());

            //saveAppointment the user with the new encrypted password

            user.setUserPassword(encryptedPassword);
            userRepo.save(user);

            return new SignUpOutput(signUpStatus, "User registered successfully!!!");
        }
        catch(Exception e)
        {
            signUpStatusMessage = "Internal error occurred during sign up";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }
    }


    public String signInUser(SignInInput signInInput) {


        String signInStatusMessage = null;

        String signInEmail = signInInput.getEmail();

        if(signInEmail == null)
        {
            signInStatusMessage = "Invalid email";
            return signInStatusMessage;


        }

        //check if this user email already exists ??
        User existingUser = userRepo.findFirstByUserEmail(signInEmail);

        if(existingUser == null)
        {
            signInStatusMessage = "Email not registered!!!";
            return signInStatusMessage;

        }

        //match passwords :

        //hash the password: encrypt the password
        try {
            String encryptedPassword = PasswordEncryptor.encryptPassword(signInInput.getPassword());
            if(existingUser.getUserPassword().equals(encryptedPassword))
            {
                //session should be created since password matched and user id is valid
                AuthenticationToken authToken  = new AuthenticationToken(existingUser);
                authenticationService.saveAuthToken(authToken);
                EmailHandler.sendEmail(signInEmail,"email testing",authToken.getTokenValue());
                return "Token sent to your email";

            }
            else {
                signInStatusMessage = "Invalid credentials!!!";
                return signInStatusMessage;
            }
        }
        catch(Exception e)
        {
            signInStatusMessage = "Internal error occurred during sign in";
            return signInStatusMessage;
        }

    }


    public String sigOutUser(String email) {

        User user = userRepo.findFirstByUserEmail(email);
        AuthenticationToken token = authenticationService.findFirstByUser(user);
        authenticationService.removeToken(token);
        return "User Signed out successfully";
    }

    public String createRecipe(Recipe recipe, String email) {
        User recipeOwner = userRepo.findFirstByUserEmail(email);
        recipe.setRecipeOwner(recipeOwner);
        recipe.setRecipeCreationTimeStamp(LocalDateTime.now());
        return recipeService.craeteRecipe(recipe);
    }




    public String deleteRecipe(Long recipeId, String email) {
        User user = userRepo.findFirstByUserEmail(email);
        return recipeService.removeRecipe(recipeId , user);

    }

    public String updateRecipe(Recipe recipe, String email) {
        User user = userRepo.findFirstByUserEmail(email);
        return recipeService.updateRecipe(recipe , user) ;
    }

    public String addComment(Comment comment, String commenterEmail) {
        boolean validRecipe = recipeService.validateRecipe(comment.getRecipe());
        if(validRecipe){
            User commenter = userRepo.findFirstByUserEmail(commenterEmail);
            comment.setCommenter(commenter);
            return commentService.addComment(comment);
        }
        else {
            return "Cannot comment on Invalid Recipe!!";
        }
    }

    public String removeComment(Long commentId, String email) {
        Comment comment  = commentService.findComment(commentId);
        if(comment!=null)
        {
            if(authorizeCommentRemover(email,comment))
            {
                commentService.removeComment(comment);
                return "comment deleted successfully";
            }
            else
            {
                return "Unauthorized delete detected...Not allowed!!!!";
            }

        }
        else
        {
            return "Invalid Comment";
        }

    }

    private boolean authorizeCommentRemover(String email, Comment comment) {
        String  commentOwnerEmail = comment.getCommenter().getUserEmail();
        String  RecipeOwnerEmail  = comment.getRecipe().getRecipeOwner().getUserEmail();

        return RecipeOwnerEmail.equals(email) || commentOwnerEmail.equals(email);
    }

    public List<Recipe> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

}
