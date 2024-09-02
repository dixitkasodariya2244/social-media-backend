package org.example.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Entity
public class StatusUpdate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "users_id")
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "statusUpdate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    public StatusUpdate() {
    }

    public StatusUpdate(String text, User user) {
        this.text = text;
        this.user = user;
        this.timestamp = LocalDateTime.now();
    }
    @OneToMany(mappedBy = "statusUpdate", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Reaction> reactions;
    // Getters and Setters
    // ...
}
