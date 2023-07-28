package com.example.RecipeManagement.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeId ;

    @NotBlank(message = "Recipe Name Required")
    private String recipeName ;

    @NotBlank(message = "Ingredients Required")
    private String ingredients ;

    @NotBlank(message = "Instructions Required")
    private String Instruction ;

    @NotBlank(message = "Cuisine Required")
    private String cuisine;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime recipeCreationTimeStamp ;
    //Time is in minutes
    @Range(min = 5 , max = 120)
    private Integer total_time_in_min;

    @Range(min = 1 , max = 10)
    private Integer servings;

    @ManyToOne
    @JoinColumn(name = "fk_recipeOwner_Id")
    private User recipeOwner ;

}
