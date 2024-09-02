package org.example.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.entity.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SimpleUserDTO {
    private Long id;
    private String name;
    private SimpleUserDTO convertToSimpleUserDTO(User user) {
        return new SimpleUserDTO(user.getId(), user.getName());
    }
    // Getters and setters...
}

