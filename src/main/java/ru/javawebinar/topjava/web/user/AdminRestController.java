package ru.javawebinar.topjava.web.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.javawebinar.topjava.model.User;

import java.util.List;

@Controller
public class AdminRestController extends AbstractUserController {

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String getAll(Model model) {
        model.addAttribute("userList", super.getAll());
        return "users";
    }

    @RequestMapping(params = "id", method = RequestMethod.GET)
    public User get(Integer id) {
        return super.get(id);
    }

    @Override
    public User create(User user) {
        return super.create(user);
    }

    @Override
    public void delete(Integer id) {
        super.delete(id);
    }

    @Override
    public void update(User user, Integer id) {
        super.update(user, id);
    }

    @Override
    public User getByMail(String email) {
        return super.getByMail(email);
    }
}
