package org.example.Services;

import org.example.Entity.Comment;
import org.example.Entity.StatusUpdate;
import org.example.Entity.User;
import org.example.Repository.CommentRepository;
import org.example.Repository.StatusUpdateRepository;
import org.example.Repository.UserRepository;
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
