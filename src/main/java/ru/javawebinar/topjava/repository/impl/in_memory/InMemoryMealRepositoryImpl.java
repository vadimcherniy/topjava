package ru.javawebinar.topjava.repository.impl.in_memory;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@Repository("inMemoryMealRepositoryImpl")
public class InMemoryMealRepositoryImpl implements MealRepository {

    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        save(new Meal(null, USER_ID, LocalDateTime.of(2018, Month.AUGUST, 21, 10, 0), "Завтрак", 500), USER_ID);
        save(new Meal(null, USER_ID, LocalDateTime.of(2018, Month.AUGUST, 21, 13, 0), "Обед", 1000), USER_ID);
        save(new Meal(null, USER_ID, LocalDateTime.of(2018, Month.AUGUST, 21, 20, 0), "Ужин", 500), USER_ID);
        save(new Meal(null, USER_ID, LocalDateTime.of(2018, Month.AUGUST, 22, 10, 0), "Завтрак", 1000), USER_ID);
        save(new Meal(null, USER_ID, LocalDateTime.of(2018, Month.AUGUST, 22, 13, 0), "Обед", 500), USER_ID);
        save(new Meal(null, USER_ID, LocalDateTime.of(2018, Month.AUGUST, 22, 20, 0), "Ужин", 510), USER_ID);

        save(new Meal(null, ADMIN_ID, LocalDateTime.of(2018, Month.AUGUST, 21, 13, 0), "Admin Обед", 500), ADMIN_ID);
        save(new Meal(null, ADMIN_ID, LocalDateTime.of(2018, Month.AUGUST, 22, 20, 0), "Admin Ужин", 510), ADMIN_ID);
    }

    @Override
    public Meal save(Meal meal, Integer userId) {
        Map<Integer, Meal> meals = repository.computeIfAbsent(userId, mealMap -> new ConcurrentHashMap<>());
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        meals.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(Integer id, Integer userId) {
        Map<Integer, Meal> meals = repository.get(userId);
        Meal meal = meals != null ? meals.get(id) : null;
        if (meal != null && meal.getUserId().equals(userId)) {
            meals.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public Meal get(Integer id, Integer userId) {
        Map<Integer, Meal> meals = repository.get(userId);
        Meal meal = meals != null ? meals.get(id) : null;
        if (meal != null && meal.getUserId().equals(userId)) {
            meals.remove(id);
            return meal;
        }
        return null;
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, Integer userId) {
        return getAllFiltered(userId, meal -> DateTimeUtil.isBetween(meal.getDateTime(), startDateTime, endDateTime));
    }

    private List<Meal> getAllFiltered(Integer userId, Predicate<Meal> filter) {
        Map<Integer, Meal> meals = repository.get(userId);
        return CollectionUtils.isEmpty(meals) ? Collections.emptyList() :
                meals.values().stream()
                        .filter(filter)
                        .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                        .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getAll(Integer userId) {
        Map<Integer, Meal> meals = repository.get(userId);
        List<Meal> result = new ArrayList(meals.values());
        return result.stream().filter(m -> m.getUserId().equals(userId)).collect(Collectors.toList());
    }
}
