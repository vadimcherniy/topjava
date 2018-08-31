package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface MealRepository {

    Meal save(Meal meal, Integer userId);

    boolean delete(Integer id, Integer userId);

    Meal get(Integer id, Integer userId);

    List<Meal> getBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, Integer userId);

    List getAll(Integer userId);
}
