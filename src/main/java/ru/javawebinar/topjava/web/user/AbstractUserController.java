package ru.javawebinar.topjava.web.user;

import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;

import java.util.List;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

public abstract class AbstractUserController {

    private static final Logger LOG = getLogger(AbstractUserController.class);

    private UserService service;

    public List<User> getAll() {
        LOG.info("getAll");
        return service.getAll();
    }

    public User get(Long id) {
        LOG.info("get {}", id);
        return service.get(id);
    }

    public User create(User user) {
        LOG.info("create {}", user);
        checkNew(user);
        return service.create(user);
    }

    public void delete(Long id) {
        LOG.info("delete {}", id);
        service.delete(id);
    }

    public void update(User user, Long id) {
        LOG.info("update {} with id={}", user, id);
        assureIdConsistent(user, id);
        service.update(user);
    }

    public User getByMail(String email) {
        LOG.info("getByEmail {}", email);
        return service.getByEmail(email);
        }

}
