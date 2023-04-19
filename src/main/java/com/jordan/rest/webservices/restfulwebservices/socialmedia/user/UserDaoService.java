package com.jordan.rest.webservices.restfulwebservices.socialmedia.user;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

//This service implements methods to interact with database
@Component
public class UserDaoService {
    private static List<User> users = new ArrayList<>();
    private static int userCount = 0;
    static {
        users.add(new User(++userCount, "Jordan", LocalDate.now().minusYears(20)));
        users.add(new User(++userCount, "Jay", LocalDate.now().minusYears(10)));
        users.add(new User(++userCount, "Kristen", LocalDate.now().minusYears(12)));
        users.add(new User(++userCount, "Max", LocalDate.now().minusYears(4)));
        users.add(new User(++userCount, "Charlie", LocalDate.now().minusYears(1)));
    }

    public List<User> findAll() {
        return users;
    }

    public User save(User user) {
        user.setId(++userCount);
        users.add(user);
        return user;
    }

    public User findOne(int id) {
        Predicate<User> findById = user -> user.getId().equals(id);
        return users.stream().filter(findById).findFirst().orElse(null);
    }

    public void deleteUserById(int id) {
        Predicate<User> findById = user -> user.getId().equals(id);
        users.removeIf(findById);
    }
}
