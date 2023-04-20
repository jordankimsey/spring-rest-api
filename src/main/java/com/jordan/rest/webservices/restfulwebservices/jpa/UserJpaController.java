package com.jordan.rest.webservices.restfulwebservices.jpa;

import com.jordan.rest.webservices.restfulwebservices.socialmedia.user.User;
import com.jordan.rest.webservices.restfulwebservices.socialmedia.user.UserDaoService;
import com.jordan.rest.webservices.restfulwebservices.socialmedia.user.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserJpaController {
    UserJpaService userJpaService;
    public UserJpaController( UserJpaService userJpaService) {
        this.userJpaService = userJpaService;
    }

    @GetMapping("/jpa/users")
    public List<User> getAllUsers() {
        return userJpaService.findAll();
    }

    //attach link to redirect to users using EntityModel
    //http://localhost:8080/users
    @GetMapping("/jpa/user/{userId}")
    public EntityModel<User> getUserById(@PathVariable int userId) {
        Optional<User> user = userJpaService.findById(userId);
        if (user.isEmpty()) {
            throw new UserNotFoundException("id" + userId);
        }
        //inits model where we can add links to be returned
        EntityModel<User> entityModel = EntityModel.of(user.get());
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).getAllUsers());
        //name key on object with the link value
        entityModel.add(link.withRel("all-users"));
        return entityModel;
    }

    @DeleteMapping("/jpa/user/{userId}")
    public void deleteUser(@PathVariable int userId) {
        userJpaService.deleteById(userId);
    }

    @PostMapping("/jpa/user")
    public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
        User createdUser = userJpaService.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
}
