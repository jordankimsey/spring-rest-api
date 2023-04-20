package com.jordan.rest.webservices.restfulwebservices.jpa;

import com.jordan.rest.webservices.restfulwebservices.socialmedia.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}
