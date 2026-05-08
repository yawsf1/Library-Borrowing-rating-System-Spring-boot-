package com.testing.bookspringapp.user;

import com.testing.bookspringapp.PageResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserDTOMapper userDTOMapper;
    private final PasswordEncoder passwordEncoder;

    public UserResponse getUser(Integer id){
        return userRepository.findById(id).map(userDTOMapper).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }
    public void addUser(UserRequest userRequest){
        userRepository.save(userDTOMapper.toEntity(userRequest));
    }

    public void modifyUser(Authentication authentication, UserRequest userRequest) {
        User user = userRepository.findById(((User)authentication.getPrincipal()).getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setUsername(userRequest.username());
        user.setEmail(userRequest.email());
        user.setPassword(userRequest.password());
        if (userRequest.password() != null && !userRequest.password().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userRequest.password()));
        }
        userRepository.save(user);
    }

    public void deleteUser(Authentication authentication) {
        Integer id = ((User) authentication.getPrincipal()).getId();
        userRepository.deleteById(id);
        SecurityContextHolder.clearContext();
    }

    public PageResponse<UserResponse> getUsers(
            int page, int size, String sortBy
    ){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<UserResponse> pageResponses = userRepository.findAll(pageable).map(userDTOMapper);
        return new PageResponse<>(
                pageResponses.getContent(),
                pageResponses.getNumber(),
                pageResponses.getSize(),
                pageResponses.getTotalElements(),
                pageResponses.getTotalPages(),
                pageResponses.isLast()
        );
    }
}