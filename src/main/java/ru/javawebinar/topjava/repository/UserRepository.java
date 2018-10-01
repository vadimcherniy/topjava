package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.User;

import java.util.List;

public interface UserRepository {

    User save(User user);

    // false if not found
    boolean delete(Integer id);

    // null if not found
    User get(Integer id);

    // null if not found
    User getByEmail(String email);

    List<User> getAll();

    default User getWithMeals(Integer id) {
        throw new UnsupportedOperationException();
    }
}
