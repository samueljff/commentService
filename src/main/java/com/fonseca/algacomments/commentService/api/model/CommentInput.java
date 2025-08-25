package com.fonseca.algacomments.commentService.api.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentInput {
    @NotBlank(message = "Texto é Obrigatório!")
    private String text;
    @NotBlank(message = "Autor é Obrigatório")
    private String author;
}