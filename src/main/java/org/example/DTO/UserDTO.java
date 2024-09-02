package org.example.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.Entity.User;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

        private Long id;
        private String name;
        private String email;
        private Integer age;
        private String phone;
        private String gender;
        private String address;
        private String password;

        // For the list of status updates
        private List<StatusUpdateDTO> statusUpdates;

        // For the set of friends
        private Set<FriendDTO> friends;

        // For the set of following
        private Set<FollowingDTO> following;

        // For the set of followers
        private Set<FollowerDTO> followers;
}
