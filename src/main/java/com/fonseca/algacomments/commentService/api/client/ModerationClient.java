package com.fonseca.algacomments.commentService.api.client;

import com.fonseca.algacomments.commentService.api.model.ModerationRequest;
import com.fonseca.algacomments.commentService.api.model.ModerationResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

public interface ModerationClient {
    @PostExchange("/api/moderate")
    ModerationResponse moderateComment(@RequestBody ModerationRequest request);
}