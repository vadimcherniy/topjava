package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface UserMealDao {

    Meal save(Meal meal);

    void delete(Long id);

    Meal get(Long id);

    List getAll();
}
