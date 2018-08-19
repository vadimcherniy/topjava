package ru.javawebinar.topjava.repository.impl;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {

    private Map<Long, Meal> repository = new ConcurrentHashMap<>();
    private AtomicLong counter = new AtomicLong(0);

    {
        save(new Meal(null, 1L, LocalDateTime.of(2018, Month.MAY, 30, 10, 0), "Завтрак", 500), 1L);
        save(new Meal(null, 1L, LocalDateTime.of(2018, Month.MAY, 30, 13, 0), "Обед", 1000), 1L);
        save(new Meal(null, 1L, LocalDateTime.of(2018, Month.MAY, 30, 20, 0), "Ужин", 500), 1L);
        save(new Meal(null, 1L, LocalDateTime.of(2018, Month.MAY, 31, 10, 0), "Завтрак", 1000), 1L);
        save(new Meal(null, 1L, LocalDateTime.of(2018, Month.MAY, 31, 13, 0), "Обед", 500), 1L);
        save(new Meal(null, 1L, LocalDateTime.of(2018, Month.MAY, 31, 20, 0), "Ужин", 510), 1L);

        save(new Meal(null, 0L, LocalDateTime.of(2018, Month.MAY, 31, 13, 0), "Обед", 500), 0L);
        save(new Meal(null, 0L, LocalDateTime.of(2018, Month.MAY, 31, 20, 0), "Ужин", 510), 0L);
    }

    @Override
    public Meal save(Meal meal, Long userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
        } else {
            repository.put(meal.getId(), meal);
        }
        return meal;
    }

    @Override
    public boolean delete(Long id, Long userId) {
        Meal meal = get(id, userId);
        if (meal != null) {
            repository.remove(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Meal get(Long id, Long userId) {
        Meal meal = repository.get(id);
        return meal.getUserId().equals(userId) ? meal : null;
    }

    @Override
    public List<Meal> getAll(Long userId) {
        List<Meal> result = new  ArrayList(repository.values());
        return result.stream().filter(m -> m.getUserId().equals(userId)).collect(Collectors.toList());
    }
}
