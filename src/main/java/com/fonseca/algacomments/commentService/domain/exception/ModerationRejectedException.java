package com.fonseca.algacomments.commentService.domain.exception;

public class ModerationRejectedException extends RuntimeException {
    public ModerationRejectedException(String message) {
        super(message);
    }
}
