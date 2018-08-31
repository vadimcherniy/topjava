package ru.javawebinar.topjava.repository.impl.spring_template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository("jdbcTemplateUserRepositoryImpl")
public class JdbcTemplateUserRepositoryImpl implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private SimpleJdbcInsert jdbcInsert;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        jdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("users").usingGeneratedKeyColumns("id");
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User save(User user) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", user.getId())
                .addValue("name", user.getName())
                .addValue("email", user.getEmail())
                .addValue("password", user.getPassword())
                .addValue("registered", user.getCreated())
                .addValue("enabled", user.isEnabled())
                .addValue("calories_per_day", user.getCaloriesPerDay());
        if (user.isNew()) {
            user.setId((long)jdbcInsert.executeAndReturnKey(map));
        } else {

        }
        return user;
    }

    @Override
    public boolean delete(long id) {
        List<User> userList = jdbcTemplate.query("DELETE FROM users WHERE id = ?", ROW_MAPPER, id);
        return !userList.isEmpty();
    }

    @Override
    public User get(long id) {
        List<User> userList = jdbcTemplate.query("SELECT * FROM users WHERE id = ?", ROW_MAPPER, id);
        return userList.isEmpty() ? null : userList.get(0);
    }

    @Override
    public User getByEmail(String email) {
        List<User> userList = jdbcTemplate.query("SELECT * FROM users WHERE email = ?", ROW_MAPPER, email);
        return userList.isEmpty() ? null : userList.get(0);
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("SELECT * FROM users ORDER BY name, email", ROW_MAPPER);
    }
}
