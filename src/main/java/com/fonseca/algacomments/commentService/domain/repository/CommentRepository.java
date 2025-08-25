package com.fonseca.algacomments.commentService.domain.repository;


import com.fonseca.algacomments.commentService.domain.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
    // Método para buscar ordenado por data de criação (mais recentes primeiro)
    @Query("SELECT c FROM Comment c ORDER BY c.createdAt DESC")
    Page<Comment> findAllOrderByCreatedAtDesc(Pageable pageable);
}