package org.example.DTO;

import lombok.*;
import org.example.entity.ReactionType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReactionDTO {

    private Long id;

    private Long userId; // Refers to the ID of the User associated with the reaction

    private Long statusUpdateId; // Refers to the ID of the StatusUpdate associated with the reaction

    private ReactionType type; // The type of reaction (e.g., LIKE, LOVE, etc.)
}
