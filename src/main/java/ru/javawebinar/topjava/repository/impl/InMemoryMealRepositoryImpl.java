package ru.javawebinar.topjava.repository.impl;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {

    private Map<Long, Map<Long, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicLong counter = new AtomicLong(0);

    {
        save(new Meal(null, 1L, LocalDateTime.of(2018, Month.AUGUST, 21, 10, 0), "Завтрак", 500), 1L);
        save(new Meal(null, 1L, LocalDateTime.of(2018, Month.AUGUST, 21, 13, 0), "Обед", 1000), 1L);
        save(new Meal(null, 1L, LocalDateTime.of(2018, Month.AUGUST, 21, 20, 0), "Ужин", 500), 1L);
        save(new Meal(null, 1L, LocalDateTime.of(2018, Month.AUGUST, 22, 10, 0), "Завтрак", 1000), 1L);
        save(new Meal(null, 1L, LocalDateTime.of(2018, Month.AUGUST, 22, 13, 0), "Обед", 500), 1L);
        save(new Meal(null, 1L, LocalDateTime.of(2018, Month.AUGUST, 22, 20, 0), "Ужин", 510), 1L);

        save(new Meal(null, 0L, LocalDateTime.of(2018, Month.AUGUST, 21, 13, 0), "Admin Обед", 500), 0L);
        save(new Meal(null, 0L, LocalDateTime.of(2018, Month.AUGUST, 22, 20, 0), "Admin Ужин", 510), 0L);
    }

    @Override
    public Meal save(Meal meal, Long userId) {
        Map<Long, Meal> meals = repository.computeIfAbsent(userId, mealMap -> new ConcurrentHashMap<>());
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        meals.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(Long id, Long userId) {
        Map<Long, Meal> meals = repository.get(userId);
        Meal meal = meals != null ? meals.get(id) : null;
        if (meal != null && meal.getUserId().equals(userId)) {
            meals.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public Meal get(Long id, Long userId) {
        Map<Long, Meal> meals = repository.get(userId);
        Meal meal = meals != null ? meals.get(id) : null;
        if (meal != null && meal.getUserId().equals(userId)) {
            meals.remove(id);
            return meal;
        }
        return null;
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, Long userId) {
        return getAllFiltered(userId, meal -> DateTimeUtil.isBetween(meal.getDateTime(), startDateTime, endDateTime));
    }

    private List<Meal> getAllFiltered(Long userId, Predicate<Meal> filter) {
        Map<Long, Meal> meals = repository.get(userId);
        return CollectionUtils.isEmpty(meals) ? Collections.emptyList() :
                meals.values().stream()
                        .filter(filter)
                        .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                        .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getAll(Long userId) {
        Map<Long, Meal> meals = repository.get(userId);
        List<Meal> result = new ArrayList(meals.values());
        return result.stream().filter(m -> m.getUserId().equals(userId)).collect(Collectors.toList());
    }
}
