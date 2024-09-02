package org.example.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ConfigurationProperties(prefix = "file")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    private String imageUrl;

    private String gifUrl;

    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "status_update_id")
    @JsonBackReference
    private StatusUpdate statusUpdate;

    @ManyToOne
    @JoinColumn(name = "users_id")
    @JsonBackReference
    private User user;

    public Comment(String text, String imageUrl, String gifUrl, StatusUpdate statusUpdate, User user) {
        this.text = text;
        this.imageUrl = imageUrl;
        this.gifUrl = gifUrl;
        this.statusUpdate = statusUpdate;
        this.user = user;
        this.timestamp = LocalDateTime.now();
    }
}
