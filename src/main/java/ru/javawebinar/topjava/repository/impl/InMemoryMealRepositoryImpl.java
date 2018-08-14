package ru.javawebinar.topjava.repository.impl;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryMealRepositoryImpl implements MealRepository {

    private Map<Long, Meal> repository = new ConcurrentHashMap<>();
    private AtomicLong counter = new AtomicLong(0);

    {
        save(new Meal(null, LocalDateTime.of(2018, Month.MAY, 30, 10, 0), "Завтрак", 500));
        save(new Meal(null, LocalDateTime.of(2018, Month.MAY, 30, 13, 0), "Обед", 1000));
        save(new Meal(null, LocalDateTime.of(2018, Month.MAY, 30, 20, 0), "Ужин", 500));
        save(new Meal(null, LocalDateTime.of(2018, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        save(new Meal(null, LocalDateTime.of(2018, Month.MAY, 31, 13, 0), "Обед", 500));
        save(new Meal(null, LocalDateTime.of(2018, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
        } else {
            repository.put(meal.getId(), meal);
        }
        return meal;
    }

    @Override
    public void delete(Long id) {
        repository.remove(id);
    }

    @Override
    public Meal get(Long id) {
        return repository.get(id);
    }

    @Override
    public List getAll() {
        return new ArrayList(repository.values());
    }
}
