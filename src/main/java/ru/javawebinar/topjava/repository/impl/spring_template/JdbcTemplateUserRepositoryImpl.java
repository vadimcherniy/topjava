package ru.javawebinar.topjava.repository.impl.spring_template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.List;

@Repository("jdbcTemplateUserRepositoryImpl")
public class JdbcTemplateUserRepositoryImpl implements UserRepository {


    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public User get(long id) {
        return null;
    }

    @Override
    public User getByEmail(String email) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }
}
