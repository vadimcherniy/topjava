package ru.javawebinar.topjava.repository.impl;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.model.User;

import javax.annotation.PostConstruct;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {

    private Map<Long, User> repository = new ConcurrentHashMap<>();
    private AtomicLong counter = new AtomicLong(2);

    public static final long ADMIN_ID = 1;
    public static final long USER_ID = 2;

    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ADMIN);
    public static final User USER = new User(USER_ID, "User", "user@yandex.ru", "password", Role.USER);

    public void init() {
        repository.clear();
        repository.put(USER_ID, USER);
        repository.put(ADMIN_ID, ADMIN);
    }

    @Override
    public User save(User user) {
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
            repository.put(user.getId(), user);
            return user;
        }
        return repository.computeIfPresent(user.getId(), (id, oldUser) -> user);
    }

    @Override
    public boolean delete(long id) {
        return repository.remove(id) != null;
    }

    @Override
    public User get(long id) {
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        return repository.values().stream()
                .sorted(Comparator.comparing(User::getName).thenComparing(User::getEmail))
                .collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        return repository.values().stream()
                .filter(u -> email.equals(u.getEmail()))
                .findFirst()
                .orElse(null);
    }
}
