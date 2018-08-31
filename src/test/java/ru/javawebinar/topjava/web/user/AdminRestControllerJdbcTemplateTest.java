package ru.javawebinar.topjava.web.user;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Collection;
import java.util.List;

@ContextConfiguration("classpath:spring/spring-app.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class AdminRestControllerJdbcTemplateTest {

    @Autowired
    private AdminRestController controller;

    @Test
    public void create() {
        List<User> users = controller.getAll();
        User newUser = new User(null, "User", "user@yandex.ru", "password", Role.USER);
        controller.create(newUser);
        Assert.assertEquals(controller.getAll().size(), users.size() + 1);
    }
}
