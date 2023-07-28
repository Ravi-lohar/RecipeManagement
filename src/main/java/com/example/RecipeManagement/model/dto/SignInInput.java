package com.example.RecipeManagement.model.dto;

import jakarta.persistence.Access;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInInput {

    @Email(message = "Invalid Email")
    private String email;
    private String password;
}
