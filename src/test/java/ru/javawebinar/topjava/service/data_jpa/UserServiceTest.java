package ru.javawebinar.topjava.service.data_jpa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration("classpath:spring/spring-app.xml")
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(Profiles.DATA_JPA)
public class UserServiceTest {

    @Autowired
    private UserService service;

    @Test
    public void create() {
        User newUser = getCreated();
        newUser.setId(service.create(newUser).getId());
        assertMatch(service.getAll(), ADMIN, USER, newUser);
    }

    @Test(expected = DataAccessException.class)
    public void createDuplicate() {
        User duplicate = new User(null, "Duplicate", "user@yandex.ru", "pass", Role.USER);
        service.create(duplicate);
    }

    @Test
    public void delete() {
        service.delete(USER_ID);
        assertMatch(service.getAll(), ADMIN);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() {
        service.delete(-1);
    }

    @Test
    public void get() {
        assertMatch(service.get(USER_ID), USER);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() {
        service.get(-1);
    }

    @Test
    public void getByEmail() {
        User user = service.getByEmail(USER.getEmail());
        assertMatch(user, USER);
    }

    @Test(expected = NotFoundException.class)
    public void getByEmailNotFound() {
        service.getByEmail("fake@user.com");
    }

    @Test
    public void update() {
        service.update(getUpdated());
        assertMatch(service.get(USER_ID), getUpdated());
    }

    @Test
    public void getAll() {
        assertMatch(service.getAll(), ADMIN, USER);
    }

    @Test
    public void getWithMeals() {
        assertMatch(service.getWithMeals(USER_ID), USER);
    }
}