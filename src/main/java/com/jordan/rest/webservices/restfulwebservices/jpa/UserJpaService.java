package com.jordan.rest.webservices.restfulwebservices.jpa;

import com.jordan.rest.webservices.restfulwebservices.socialmedia.user.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserJpaService {
private UserRepository userRepository;
public UserJpaService(UserRepository userRepository) {
    this.userRepository = userRepository;
}
    public User save(User user) {
        return userRepository.save(user);
    }

    public void deleteById(int userId) {
        userRepository.deleteById(userId);
    }

    public Optional<User> findById(int userId) {
        return userRepository.findById(userId);
    }

    public List<User> findAll() {
    return userRepository.findAll();
    }
}
