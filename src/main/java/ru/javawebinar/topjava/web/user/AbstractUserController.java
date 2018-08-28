package ru.javawebinar.topjava.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

public abstract class AbstractUserController {

    @Autowired
    private UserService service;

    public List<User> getAll() {
        return service.getAll();
    }

    public User get(Long id) {
        return service.get(id);
    }

    public User create(User user) {
        checkNew(user);
        return service.create(user);
    }

    public void delete(Long id) {
        service.delete(id);
    }

    public void update(User user, Long id) {
        assureIdConsistent(user, id);
        service.update(user);
    }

    public User getByMail(String email) {
        return service.getByEmail(email);
        }

}
