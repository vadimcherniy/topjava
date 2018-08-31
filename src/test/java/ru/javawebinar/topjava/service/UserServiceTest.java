package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.UserTestData.ADMIN;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration("classpath:spring/spring-app.xml")
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {

    @Autowired
    private UserService service;

    @Test
    public void create() {
        User newUser = new User(null, "newUser", "user@user.com", "pass", Role.USER);
        newUser.setId(service.create(newUser).getId());
        List<User> actual = service.getAll();
        List<User> expected = Arrays.asList(ADMIN, newUser, USER);
        assertThat(actual).usingElementComparatorIgnoringFields("created", "roles").isEqualTo(expected);
    }

    @Test(expected = DataAccessException.class)
    public void createDuplicate() {
        User duplicate = new User(null, "Duplicate", "user@yandex.ru", "pass", Role.USER);
        service.create(duplicate);
    }

    @Test
    public void delete() {
    }

    @Test
    public void get() {
        User user = service.get(USER_ID);
        assertThat(user).isEqualToIgnoringGivenFields(USER, "created", "roles");
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() {
        service.get(-1);
    }

    @Test
    public void getByEmail() {
        User user = service.getByEmail(ADMIN.getEmail());
        assertThat(user).isEqualToIgnoringGivenFields(ADMIN, "created", "roles");
    }

    @Test(expected = NotFoundException.class)
    public void getByEmailNotFound() {
        service.getByEmail("fake@user.com");
    }

    @Test
    public void update() {
    }

    @Test
    public void getAll() {
    }
}