package ru.javawebinar.topjava.web.user;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.web.WebTest;

@ActiveProfiles({Profiles.DATAJPA, Profiles.POSTGRES})
public class AdminRestControllerTest extends WebTest {

    @Test
    public void getAll() {
    }

    @Test
    public void get() {
    }

    @Test
    public void create() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void update() {
    }

    @Test
    public void getByMail() {
    }
}