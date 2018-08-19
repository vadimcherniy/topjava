package ru.javawebinar.topjava.repository.impl;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.model.User;

import java.util.List;

@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public User get(long id) {
        return null;
    }

    @Override
    public User getByEmail(String email) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }
}
