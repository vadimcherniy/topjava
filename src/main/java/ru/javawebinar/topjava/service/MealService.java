package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface MealService {

    Meal crete(Meal meal, Long userId);

    Meal update(Meal meal, Long userId);

    void delete(Long id, Long userId);

    Meal get(Long id, Long userId);

    default List<Meal> getBetweenDates(LocalDate startDate, LocalDate endDate, Long userId) {
        return getBetweenDateTimes(LocalDateTime.of(startDate, LocalTime.MIN), LocalDateTime.of(endDate, LocalTime.MAX), userId);
    }

    List<Meal> getBetweenDateTimes(LocalDateTime startDateTime, LocalDateTime endDateTime, Long userId);

    List getAll(Long userId);
}
