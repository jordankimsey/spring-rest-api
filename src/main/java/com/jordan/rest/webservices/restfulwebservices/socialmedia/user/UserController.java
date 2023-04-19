package com.jordan.rest.webservices.restfulwebservices.socialmedia.user;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserController {
    UserDaoService userService;

    public UserController(UserDaoService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/user/{userId}")
    public User getUserById(@PathVariable int userId) {
        User user = userService.findOne(userId);
        if (user == null) {
            throw new UserNotFoundException("id" + userId);
        }
        return user;
    }

    @DeleteMapping("/user/{userId}")
    public void deleteUser(@PathVariable int userId){
        userService.deleteUserById(userId);
    }

    @PostMapping("/user")
    public ResponseEntity<User> addUser(@Valid @RequestBody User newUser) {
        User createdUser = userService.save(newUser);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }


}
