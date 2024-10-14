package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FollowRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "follower_id", nullable = false)
    private User follower;  // Reference to the User entity as the follower

    @ManyToOne
    @JoinColumn(name = "following_id", nullable = false)
    private User following;

    @Column(name = "status")
    private String status;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public FollowRequest(User follower, User following, String status) {
        this.follower = follower;
        this.following = following;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }
}
