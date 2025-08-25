package com.fonseca.algacomments.commentService.api.controller;

import com.fonseca.algacomments.commentService.api.model.CommentInput;
import com.fonseca.algacomments.commentService.api.model.CommentOutput;
import com.fonseca.algacomments.commentService.domain.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
@Validated
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentOutput createComment(@Valid @RequestBody CommentInput input) {
        return commentService.createComment(input);
    }

    @GetMapping("/{id}")
    public CommentOutput findCommentById(@PathVariable String id) {
        return commentService.findCommentById(UUID.fromString(id));
    }

    @GetMapping
    public Page<CommentOutput> findAllComments(Pageable pageable) {
        return commentService.findAllComments(pageable);
    }
}
