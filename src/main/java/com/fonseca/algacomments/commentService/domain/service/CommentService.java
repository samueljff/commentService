package com.fonseca.algacomments.commentService.domain.service;

import com.fonseca.algacomments.commentService.api.client.ModerationClient;
import com.fonseca.algacomments.commentService.api.model.CommentInput;
import com.fonseca.algacomments.commentService.api.model.CommentOutput;
import com.fonseca.algacomments.commentService.api.model.ModerationRequest;
import com.fonseca.algacomments.commentService.api.model.ModerationResponse;
import com.fonseca.algacomments.commentService.domain.exception.CommentNotFoundException;
import com.fonseca.algacomments.commentService.domain.exception.ModerationRejectedException;
import com.fonseca.algacomments.commentService.domain.model.Comment;
import com.fonseca.algacomments.commentService.domain.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ModerationClient moderationClient;

    public CommentOutput createComment(CommentInput input) {
        Comment comment = Comment.builder()
                .text(input.getText())
                .author(input.getAuthor())
                .build();

        ModerationRequest request = new ModerationRequest(comment.getId(), input.getText());
        ModerationResponse response = moderationClient.moderateComment(request);

        if (!response.isApproved()) {
            throw new ModerationRejectedException(response.getReason());
        }

        Comment savedComment = commentRepository.saveAndFlush(comment);
        return mapToOutput(savedComment);
    }

    public CommentOutput findCommentById(UUID id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(CommentNotFoundException::new);
        return mapToOutput(comment);
    }

    public Page<CommentOutput> findAllComments(Pageable pageable) {
        return commentRepository.findAllOrderByCreatedAtDesc(pageable)
                .map(this::mapToOutput);
    }

    private CommentOutput mapToOutput(Comment comment) {
        return CommentOutput.builder()
                .id(comment.getId())
                .text(comment.getText())
                .author(comment.getAuthor())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}