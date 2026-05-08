package com.testing.bookspringapp.user;


import com.testing.bookspringapp.PageResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @GetMapping(path = "{id}")
    public UserResponse getUser(@PathVariable Integer id){
        return userService.getUser(id);
    }

    @GetMapping
    public PageResponse<UserResponse> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ){
        return userService.getUsers(page, size, sortBy);
    }
    /*@PostMapping
    public void addUser(@Valid @RequestBody UserRequest userRequest){
        userService.addUser(userRequest);
    }*/

    @PutMapping
    public void modifyUser(
            @Valid @RequestBody UserRequest userRequest,
            Authentication authentication
            ){
        userService.modifyUser(authentication,userRequest);
    }

    @DeleteMapping
    public void deleteUser(Authentication authentication){
        userService.deleteUser(authentication);
    }
}
