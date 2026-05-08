package com.testing.bookspringapp.user;


import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UserDTOMapper implements Function<User, UserResponse> {
    @Override
    public UserResponse apply(User user) {
        return new UserResponse(
                user.getUsername(),
                user.getEmail()
        );
    }
    public User toEntity(UserRequest request){
        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(request.password());
        user.setRole(User.Role.USER);
        return user;
    }
}
