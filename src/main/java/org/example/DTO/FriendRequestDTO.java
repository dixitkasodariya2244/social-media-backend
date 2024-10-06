package org.example.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FriendRequestDTO {
    private Long id;
    private Long senderId;
    private String senderName;
    private Long receiverId;
    private String status;
}
