package ru.javawebinar.topjava.web.user;

import ru.javawebinar.topjava.model.User;

import java.util.List;

public class AdminRestController extends AbstractUserController {

    @Override
    public List<User> getAll() {
        return super.getAll();
    }

    @Override
    public User get(Long id) {
        return super.get(id);
    }

    @Override
    public User create(User user) {
        return super.create(user);
    }

    @Override
    public void delete(Long id) {
        super.delete(id);
    }

    @Override
    public void update(User user, Long id) {
        super.update(user, id);
    }

    @Override
    public User getByMail(String email) {
        return super.getByMail(email);
    }
}
