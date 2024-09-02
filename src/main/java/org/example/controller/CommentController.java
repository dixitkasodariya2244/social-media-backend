package org.example.controller;

import org.example.entity.Comment;
import org.example.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/{userId}/status/{statusUpdateId}/comment")
    public ResponseEntity<Comment> postComment(@PathVariable Long userId, @PathVariable Long statusUpdateId,
                                               @RequestParam(required = false) String text,
                                               @RequestParam(required = false) String imageUrl,
                                               @RequestParam(required = false) String gifUrl) {
        Comment comment = commentService.postComment(userId, statusUpdateId, text, imageUrl, gifUrl);
        if (comment != null) {
            return ResponseEntity.ok(comment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/comment/{commentId}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long commentId) {
        Optional<Comment> comment = commentService.getCommentById(commentId);
        return comment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}
