package org.example.utils;

import org.example.DTO.*;
import org.example.entity.Reaction;
import org.example.entity.StatusUpdate;
import org.example.entity.User;

import java.util.*;
import java.util.stream.Collectors;

public class UserMapper {
    public static UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setAge(user.getAge());
        dto.setPhone(user.getPhone());
        dto.setGender(user.getGender());
        dto.setAddress(user.getAddress());
        dto.setPassword(user.getPassword());

        //get statusUpdates
        List<StatusUpdateDTO> statusUpdates = Optional.ofNullable(user.getStatusUpdates())
                .orElseGet(ArrayList::new)
                .stream()
                .map(UserMapper::toStatusUpdateDTO)
                .collect(Collectors.toList());
        dto.setStatusUpdates(statusUpdates);

        Set<FriendDTO> friends = Optional.ofNullable(user.getFriends())
                .orElseGet(HashSet::new)
                .stream()
                .map(UserMapper::toFriendDTO)
                .collect(Collectors.toSet());
        dto.setFriends(friends);
        Set<ReactionDTO> reaction = Optional.ofNullable(user.getReactions())
                .orElseGet(HashSet::new)
                .stream()
                .map(UserMapper::toReactionDTO)
                .collect(Collectors.toSet());
        dto.setFriends(friends);

        Set<FollowerDTO> followers = Optional.ofNullable(user.getFollowers())
                .orElseGet(HashSet::new)
                .stream()
                .map(UserMapper::toFollowerDTO)
                .collect(Collectors.toSet());
        dto.setFollowers(followers);

        Set<FollowingDTO> following = Optional.ofNullable(user.getFollowing())
                .orElseGet(HashSet::new)
                .stream()
                .map(UserMapper::toFollowingDTO)
                .collect(Collectors.toSet());
        dto.setFollowing(following);

        return dto;
    }
    public static User toEntity(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setAge(dto.getAge());
        user.setPhone(dto.getPhone());
        user.setGender(dto.getGender());
        user.setAddress(dto.getAddress());
        user.setPassword(dto.getPassword());

        // Convert StatusUpdateDTO to StatusUpdate entities if needed
        // Convert FriendDTO to User entities if needed

        return user;
    }

    private static StatusUpdateDTO toStatusUpdateDTO(StatusUpdate update) {
        StatusUpdateDTO statusUpdateDTO = new StatusUpdateDTO();
        statusUpdateDTO.setId(update.getId());
        statusUpdateDTO.setText(update.getText());
        statusUpdateDTO.setTimestamp(update.getTimestamp());
        return statusUpdateDTO;
    }

    private static FriendDTO toFriendDTO(User friend) {
        FriendDTO friendDTO = new FriendDTO();
        friendDTO.setId(friend.getId());
        friendDTO.setName(friend.getName());

        // Map status updates
        List<StatusUpdateDTO> statusUpdates = friend.getStatusUpdates().stream()
                .map(UserMapper::toStatusUpdateDTO)
                .collect(Collectors.toList());
        friendDTO.setStatusUpdates(statusUpdates);

        return friendDTO;
    }

    private static FollowerDTO toFollowerDTO(User follower) {
        FollowerDTO followerDTO = new FollowerDTO();
        followerDTO.setId(follower.getId());
        followerDTO.setName(follower.getName());

        // Map status updates
        List<StatusUpdateDTO> statusUpdates = follower.getStatusUpdates().stream()
                .map(UserMapper::toStatusUpdateDTO)
                .collect(Collectors.toList());
        followerDTO.setStatusUpdates(statusUpdates);

        return followerDTO;
    }

    private static FollowingDTO toFollowingDTO(User followingUser) {
        FollowingDTO followingDTO = new FollowingDTO();
        followingDTO.setId(followingUser.getId());
        followingDTO.setName(followingUser.getName());

        // Map status updates
        List<StatusUpdateDTO> statusUpdates = followingUser.getStatusUpdates().stream()
                .map(UserMapper::toStatusUpdateDTO)
                .collect(Collectors.toList());
        followingDTO.setStatusUpdates(statusUpdates);

        return followingDTO;
    }

    public static ReactionDTO toReactionDTO(Reaction reaction) {
        return ReactionDTO.builder()
                .id(reaction.getId())
                .userId(reaction.getUser().getId())
                .statusUpdateId(reaction.getStatusUpdate().getId())
                .type(reaction.getType())
                .build();
    }
}
