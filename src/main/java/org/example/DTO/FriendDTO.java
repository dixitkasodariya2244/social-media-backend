package org.example.DTO;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FriendDTO {
    private Long id;
    private String name;
    // For the list of status updates
    @JsonBackReference
    private List<StatusUpdateDTO> statusUpdates;
}
