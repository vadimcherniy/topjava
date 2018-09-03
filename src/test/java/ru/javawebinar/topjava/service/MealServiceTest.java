package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration("classpath:spring/spring-app.xml")
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@RunWith(SpringJUnit4ClassRunner.class)
public class MealServiceTest {

    @Autowired
    private MealService service;

    @Test
    public void crete() {
        Meal newMeal = new Meal(null, LocalDateTime.now(), "Diner", 1000);
        newMeal.setId(service.crete(newMeal, ADMIN_ID).getId());
        assertMatch(service.getAll(ADMIN_ID), newMeal, ADMIN_MEAL_1, ADMIN_MEAL_2);
    }

    @Test(expected = DataAccessException.class)
    public void createNotFound() {
        Meal newMeal = new Meal(null, LocalDateTime.now(), "Diner", 1000);
        service.crete(newMeal, -1);
    }

    @Test
    public void update() {
        Meal meal = new Meal(ADMIN_MEAL_1);
        meal.setCalories(1000);
        meal.setDescription("Breakfast");
        meal.setDateTime(LocalDateTime.now());
        service.update(meal, ADMIN_ID);
        assertMatch(service.get(ADMIN_MEAL_1_ID, ADMIN_ID), meal);
    }

    @Test
    public void delete() {
        service.delete(ADMIN_MEAL_1_ID, ADMIN_ID);
        assertMatch(service.getAll(ADMIN_ID), ADMIN_MEAL_2);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() {
        service.delete(ADMIN_MEAL_1_ID, -1);
    }

    @Test
    public void get() {
        assertMatch(service.get(ADMIN_MEAL_1_ID, ADMIN_ID), ADMIN_MEAL_1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() {
        service.get(ADMIN_MEAL_1_ID, -1);
    }

    @Test
    public void getBetweenDates() {
        List<Meal> actual =  service.getBetweenDates(
                LocalDate.of(2018, 9, 1),
                LocalDate.of(2018, 9, 1),
                ADMIN_ID);
        assertMatch(actual, ADMIN_MEAL_1, ADMIN_MEAL_2);
    }

    @Test
    public void getBetweenDateTimes() {
        List<Meal> actual = service.getBetweenDateTimes(
                LocalDateTime.of(2018, 9, 1, 8, 0, 0),
                LocalDateTime.of(2018, 9, 1, 12, 0, 0),
                ADMIN_ID);
        assertMatch(actual, ADMIN_MEAL_2);
    }

    @Test
    public void getAll() {
        assertMatch(service.getAll(ADMIN_ID), ADMIN_MEAL_1, ADMIN_MEAL_2);
    }

}