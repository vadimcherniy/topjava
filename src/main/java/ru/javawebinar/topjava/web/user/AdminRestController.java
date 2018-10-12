package ru.javawebinar.topjava.web.user;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.User;

import java.util.List;

@RestController("/rest/admin/users")
public class AdminRestController extends AbstractUserController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAll() {
        return super.getAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User get(@PathVariable("id") Integer id) {
        return super.get(id);
    }

    public User create(User user) {
        return super.create(user);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") Integer id) {
        super.delete(id);
    }

    public void update(User user, Integer id) {
        super.update(user, id);
    }

    public User getByMail(String email) {
        return super.getByMail(email);
    }
}
