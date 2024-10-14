package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "reactions", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"users_id", "status_update_id"})
})
public class Reaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "status_update_id")
    private StatusUpdate statusUpdate;

    @Enumerated(EnumType.STRING)
    private ReactionType type;
}

