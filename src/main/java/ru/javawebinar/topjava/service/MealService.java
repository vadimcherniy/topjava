package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface MealService {

    Meal crete(Meal meal, Integer userId);

    Meal update(Meal meal, Integer userId);

    void delete(Integer id, Integer userId);

    Meal get(Integer id, Integer userId);

    default List<Meal> getBetweenDates(LocalDate startDate, LocalDate endDate, Integer userId) {
        return getBetweenDateTimes(LocalDateTime.of(startDate, LocalTime.MIN), LocalDateTime.of(endDate, LocalTime.MAX), userId);
    }

    List<Meal> getBetweenDateTimes(LocalDateTime startDateTime, LocalDateTime endDateTime, Integer userId);

    List getAll(Integer userId);
}
