package com.example.RecipeManagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId ;

    @Pattern(regexp = "^[A-Z].*$" , message = "First Nam should be Capital")
    @NotBlank(message = "User Name is required")
    private String userName ;

    @Email(message = "Invalid Email")
    @Column(unique = true)
    private String userEmail ;


    @Pattern(regexp = "^\\d{2}\\d{10}$" , message = "Contact number must have 2 digit country code with 10 digit number")
    private String contactNumber ;

    @NotBlank
    private String userPassword ;
    @Enumerated(EnumType.STRING)
    private Gender gender ;
}
