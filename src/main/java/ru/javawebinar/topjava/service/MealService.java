package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealService {

    Meal save(Meal meal);

    void delete(Long id, Long userId);

    Meal get(Long id, Long userId);

    List getAll(Long userId);
}
