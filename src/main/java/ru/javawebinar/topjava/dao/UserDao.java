package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.User;

import java.util.List;

public interface UserDao {

    User save(User user);

    // false if not found
    boolean delete(int id);

    // null if not found
    User get(int id);

    // null if not found
    User getByEmail(String email);

    List<User> getAll();
}
