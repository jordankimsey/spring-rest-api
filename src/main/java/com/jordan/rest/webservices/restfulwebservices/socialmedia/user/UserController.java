package com.jordan.rest.webservices.restfulwebservices.socialmedia.user;

import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

    //attach link to redirect to users using EntityModel
    //http://localhost:8080/users
    @GetMapping("/user/{userId}")
    public EntityModel<User> getUserById(@PathVariable int userId) {
        User user = userService.findOne(userId);
        if (user == null) {
            throw new UserNotFoundException("id" + userId);
        }
        //inits model where we can add links to be returned
        EntityModel<User> entityModel = EntityModel.of(user);
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).getAllUsers());
        //name key on object with the link value
        entityModel.add(link.withRel("all-users"));
        return entityModel;
    }

    @DeleteMapping("/user/{userId}")
    public void deleteUser(@PathVariable int userId) {
        userService.deleteUserById(userId);
    }

    @PostMapping("/user")
    public ResponseEntity<User> addUser(@Valid @RequestBody User newUser) {
        User createdUser = userService.save(newUser);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }


}
