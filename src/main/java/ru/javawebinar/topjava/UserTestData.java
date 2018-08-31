package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class UserTestData {

    public static final Integer ADMIN_ID = START_SEQ;
    public static final Integer USER_ID = START_SEQ + 1;

    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ADMIN);
    public static final User USER = new User(USER_ID, "User", "user@yandex.ru", "password", Role.USER);
}
