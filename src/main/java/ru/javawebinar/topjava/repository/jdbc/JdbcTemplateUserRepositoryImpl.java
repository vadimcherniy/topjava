package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.List;

@Repository
public class JdbcTemplateUserRepositoryImpl implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private SimpleJdbcInsert jdbcInsert;

    private JdbcTemplate jdbcTemplate;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("users").usingGeneratedKeyColumns("id");
    }

    @Override
    public User save(User user) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", user.getId())
                .addValue("name", user.getName())
                .addValue("email", user.getEmail())
                .addValue("password", user.getPassword())
                .addValue("created", user.getCreated())
                .addValue("enabled", user.isEnabled())
                .addValue("calories_per_day", user.getCaloriesPerDay());
        if (user.isNew()) {
            user.setId((int)jdbcInsert.executeAndReturnKey(map));
        } else if (namedParameterJdbcTemplate.update(
                "UPDATE users SET name=:name, email=:email, password=:password, " +
                        "created=:created, enabled=:enabled, calories_per_day=:calories_per_day WHERE id=:id", map) == 0) {
            return null;
        }
        return user;
    }

    @Override
    public boolean delete(Integer id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id = ?", id) != 0;
    }

    @Override
    public User get(Integer id) {
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
