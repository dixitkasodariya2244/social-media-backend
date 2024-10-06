package org.example.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatusUpdateDTO {
    private Long id;
    private String text;
    private String imageUrl;
    private LocalDateTime timestamp;
    private Long userId; // ID of the user who created the status
    private UploadDTO uploads;   // List of associated uploads
    private List<CommentDTO> comments; // List of associated comments
    private List<ReactionDTO> reactions; // List of associated reactions
}
