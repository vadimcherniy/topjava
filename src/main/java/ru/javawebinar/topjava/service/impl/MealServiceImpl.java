package ru.javawebinar.topjava.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealServiceImpl implements MealService {

    private MealRepository mealRepository;

    @Autowired
    public void setMealRepository(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    @Override
    public Meal crete(Meal meal, Integer userId) {
        return checkNotFoundWithId(mealRepository.save(meal, userId), meal.getId());
    }

    @Override
    public Meal update(Meal meal, Integer userId) {
        return checkNotFoundWithId(mealRepository.save(meal, userId), meal.getId());
    }

    @Override
    public void delete(Integer id, Integer userId) {
        checkNotFoundWithId(mealRepository.delete(id, userId), id);
    }

    @Override
    public Meal get(Integer id, Integer userId) {
        return checkNotFoundWithId(mealRepository.get(id, userId), id);
    }

    @Override
    public List<Meal> getBetweenDateTimes(LocalDateTime startDateTime, LocalDateTime endDateTime, Integer userId) {
        return mealRepository.getBetween(startDateTime, endDateTime, userId);
    }

    @Override
    public List getAll(Integer userId) {
        return mealRepository.getAll(userId);
    }

    @Override
    public Meal getWithUser(Integer id, Integer userId) {
        return mealRepository.getWithUser(id, userId);
    }
}
