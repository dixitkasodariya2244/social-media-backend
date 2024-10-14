package org.example.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FollowRequestDTO {
    private Long id;
    private Long follower;
    private String followerName;
    private Long following;
    private String status;
}

