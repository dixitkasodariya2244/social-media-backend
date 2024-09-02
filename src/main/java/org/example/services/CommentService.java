package org.example.services;

import org.example.entity.Comment;
import org.example.entity.StatusUpdate;
import org.example.entity.User;
import org.example.repository.CommentRepository;
import org.example.repository.StatusUpdateRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
    public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private StatusUpdateRepository statusUpdateRepository;

    @Autowired
    private UserRepository userRepository;

    public Comment postComment(Long userId, Long statusUpdateId, String text, String imageUrl, String gifUrl) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<StatusUpdate> statusUpdateOptional = statusUpdateRepository.findById(statusUpdateId);

        if (userOptional.isPresent() && statusUpdateOptional.isPresent()) {
            User user = userOptional.get();
            StatusUpdate statusUpdate = statusUpdateOptional.get();
            Comment comment = new Comment(text, imageUrl, gifUrl, statusUpdate, user);
            return commentRepository.save(comment);
        }
        return null;
    }

    public Optional<Comment> getCommentById(Long commentId) {
        return commentRepository.findById(commentId);
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
