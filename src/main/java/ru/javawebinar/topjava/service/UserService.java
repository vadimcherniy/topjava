package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

public interface UserService {

    User create(User user);

    void delete(Integer id) throws NotFoundException;

    User get(Integer id) throws NotFoundException;

    User getByEmail(String email) throws NotFoundException;

    void update(User user);

    List<User> getAll();

    User getWithMeals(Integer id) throws NotFoundException;
}
