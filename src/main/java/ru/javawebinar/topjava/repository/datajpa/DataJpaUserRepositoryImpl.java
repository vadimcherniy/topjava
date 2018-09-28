package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.List;

@Repository("dataJpaUserRepositoryImpl")
public class DataJpaUserRepositoryImpl implements UserRepository {

    private static final Sort SORT_NAME_EMAIL = new Sort("name", "email");
    private CrudUserRepository proxy;

    @Autowired
    public void setProxy(CrudUserRepository proxy) {
        this.proxy = proxy;
    }

    @Override
    public User save(User user) {
        return proxy.save(user);
    }

    @Override
    public boolean delete(Integer id) {
        return proxy.delete(id) != 0;
    }

    @Override
    public User get(Integer id) {
        return proxy.findById(id).orElse(null);
    }

    @Override
    public User getByEmail(String email) {
        return proxy.findByEmail(email);
    }

    @Override
    public List<User> getAll() {
        return proxy.findAll(SORT_NAME_EMAIL);
    }
}
