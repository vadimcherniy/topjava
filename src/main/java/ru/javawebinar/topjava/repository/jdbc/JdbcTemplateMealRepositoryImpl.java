package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JdbcTemplateMealRepositoryImpl implements MealRepository {

    private static final RowMapper<Meal> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Meal.class);

    private SimpleJdbcInsert jdbcInsert;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("meals").usingGeneratedKeyColumns("id");
    }

    @Override
    public Meal save(Meal meal, Integer userId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("user_id", userId)
                .addValue("date_time", meal.getDateTime())
                .addValue("description", meal.getDescription())
                .addValue("calories", meal.getCalories());
        if (meal.isNew()) {
            meal.setId((int) jdbcInsert.executeAndReturnKey(map));
        } else if (jdbcTemplate.update("UPDATE meals SET date_time = ?, description = ?, calories = ? " +
                        "WHERE id = ? AND user_id = ?",
                meal.getDateTime(), meal.getDescription(), meal.getCalories(), meal.getId(), userId) == 0) {
            return null;
        }
        return meal;
    }

    @Override
    public boolean delete(Integer id, Integer userId) {
        return jdbcTemplate.update("DELETE FROM meals WHERE id =? AND user_id = ?", id, userId) != 0;
    }

    @Override
    public Meal get(Integer id, Integer userId) {
        List<Meal> meals = jdbcTemplate.query("SELECT * FROM meals WHERE id = ? AND user_id = ?", ROW_MAPPER, id, userId);
        return meals.isEmpty() ? null : meals.get(0);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, Integer userId) {
        return jdbcTemplate.query("SELECT * FROM meals WHERE user_id = ? AND date_time > ? AND date_time < ?" +
                        " ORDER BY date_time DESC",
                ROW_MAPPER, userId, startDateTime, endDateTime);
    }

    @Override
    public List getAll(Integer userId) {
        return jdbcTemplate.query("SELECT * FROM meals WHERE user_id = ? ORDER BY date_time DESC", ROW_MAPPER, userId);
    }
}
