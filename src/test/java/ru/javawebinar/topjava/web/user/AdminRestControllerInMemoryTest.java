package ru.javawebinar.topjava.web.user;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.impl.in_memory.InMemoryUserRepositoryImpl;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collection;

import static ru.javawebinar.topjava.repository.impl.in_memory.InMemoryUserRepositoryImpl.ADMIN;
import static ru.javawebinar.topjava.repository.impl.in_memory.InMemoryUserRepositoryImpl.USER_ID;

@ContextConfiguration("classpath:spring/spring-app.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class AdminRestControllerInMemoryTest {

    @Autowired
    private AdminRestController controller;

    @Autowired
    private InMemoryUserRepositoryImpl repository;

    @Before
    public void init() {
        repository.init();
    }

    @Test
    public void testDelete() throws Exception {
        controller.delete(USER_ID);
        Collection<User> users = controller.getAll();
        Assert.assertEquals(users.size(), 1);
        Assert.assertEquals(users.iterator().next(), ADMIN);
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteNotFound() throws Exception {
        controller.delete(10L);
    }

    @Test
    public void create() {
        Collection<User> users = controller.getAll();
        User newUser = new User(null, "User", "user@yandex.ru", "password", Role.USER);
        controller.create(newUser);
        Assert.assertEquals(controller.getAll().size(), users.size() + 1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() {
        controller.get(-1L);
    }
}