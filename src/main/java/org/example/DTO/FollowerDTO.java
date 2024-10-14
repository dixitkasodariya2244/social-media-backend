package org.example.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FollowerDTO {
    private Long id;
    private String name;
    // For the list of status updates
    private List<StatusUpdateDTO> statusUpdates;
}
