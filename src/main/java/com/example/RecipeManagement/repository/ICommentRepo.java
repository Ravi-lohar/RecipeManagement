package com.example.RecipeManagement.repository;

import com.example.RecipeManagement.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICommentRepo extends JpaRepository<Comment , Long> {
}
