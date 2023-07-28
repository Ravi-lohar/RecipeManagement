package com.example.RecipeManagement.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(nullable = false)
    private String commentBody;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime commentCreationTimeStamp;

    @ManyToOne
    @JoinColumn(name = "fk_comment_recipe_id")
    private Recipe recipe ;

    @ManyToOne
    @JoinColumn(name = "fk_commenter_id")
    private User commenter;
}
