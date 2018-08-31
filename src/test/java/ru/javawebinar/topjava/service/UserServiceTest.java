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
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.UserTestData.*;

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
        service.delete(USER_ID);
        List<User> actual = service.getAll();
        List<User> expected = Collections.singletonList(ADMIN);
        assertThat(actual).usingDefaultElementComparator().isEqualTo(expected);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() {
        service.delete(-1);
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
        User user = service.getByEmail(USER.getEmail());
        assertThat(user).isEqualToIgnoringGivenFields(USER, "created", "roles");
    }

    @Test(expected = NotFoundException.class)
    public void getByEmailNotFound() {
        service.getByEmail("fake@user.com");
    }

    @Test
    public void update() {
        ADMIN.setName("SuperAdmin");
        service.update(ADMIN);
        User actual = service.get(ADMIN_ID);
        User expected = new User(ADMIN_ID, "SuperAdmin", "admin@gmail.com", "admin", Role.ADMIN);
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "created", "roles");
    }

    @Test
    public void getAll() {
        List<User> expected = Arrays.asList(ADMIN, USER);
        List<User> actual = service.getAll();
        assertThat(actual).usingDefaultElementComparator().isEqualTo(expected);
    }
}