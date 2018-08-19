package ru.javawebinar.topjava.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.ValidationUtil;

import java.util.List;

@Service
public class MealServiceImpl implements MealService {

    @Autowired
    private MealRepository mealRepository;

    @Override
    public Meal save(Meal meal) {
        return mealRepository.save(meal);
    }

    @Override
    public void delete(Long id, Long userId) {
        ValidationUtil.checkNotFound(mealRepository.delete(id, userId), "id = " + id);
    }

    @Override
    public Meal get(Long id, Long userId) {
        return ValidationUtil.checkNotFound(mealRepository.get(id, userId), "id = " + id);
    }

    @Override
    public List getAll(Long userId) {
        return mealRepository.getAll(userId);
    }
}
