package org.example.Controller;

import org.example.DTO.ReactionDTO;
import org.example.Entity.Reaction;
import org.example.Entity.ReactionType;
import org.example.Services.ReactionService;
import org.example.Utils.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reactions")
public class ReactionController {

    @Autowired
    private ReactionService reactionService;

    @PostMapping("/{userId}/{statusUpdateId}")
    public ResponseEntity<ReactionDTO> addReaction(
            @PathVariable Long userId,
            @PathVariable Long statusUpdateId,
            @RequestParam ReactionType type) {

        Reaction reaction = reactionService.addReaction(userId, statusUpdateId, type);
        if (reaction != null) {
            ReactionDTO reactionDTO = UserMapper.toReactionDTO(reaction);
            return ResponseEntity.ok(reactionDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{statusUpdateId}/{type}/count")
    public ResponseEntity<Integer> countReactions(
            @PathVariable Long statusUpdateId,
            @PathVariable ReactionType type) {

        int count = reactionService.countReactions(statusUpdateId, type);
        return ResponseEntity.ok(count);
    }
}
