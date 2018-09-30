package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DataJpaMealRepositoryImpl implements MealRepository {

    @Autowired
    private CrudMealRepository crudMeal;

    @Autowired
    private CrudUserRepository crudUser;

    @Override
    public Meal save(Meal meal, Integer userId) {
        if (!meal.isNew() && get(meal.getId(), userId) == null) {
            return null;
        }
        meal.setUser(crudUser.getOne(userId));
        return crudMeal.save(meal);
    }

    @Override
    public boolean delete(Integer id, Integer userId) {
        return crudMeal.delete(id, userId) != 0;
    }

    @Override
    public Meal get(Integer id, Integer userId) {
        return crudMeal.getWithUser(id, userId).orElse(null);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, Integer userId) {
        return crudMeal.getBetween(startDateTime, endDateTime, userId);
    }

    @Override
    public List getAll(Integer userId) {
        return crudMeal.getAll(userId);
    }
}
