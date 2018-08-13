package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class MealsUtil {
    private static List<Meal> meals = Arrays.asList(
            new Meal(LocalDateTime.of(2018, Month.MAY, 30, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2018, Month.MAY, 30, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2018, Month.MAY, 30, 20, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2018, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2018, Month.MAY, 31, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2018, Month.MAY, 31, 20, 0), "Ужин", 510)
    );

    public static List<Meal> getMealList() {
        return meals;
    }

    public static List<MealWithExceed> getFilteredWithExceeded(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
                );

        return meals.stream()
                .filter(meal -> DateTimeUtil.isBetween(meal.getTime(), startTime, endTime))
                .map(meal -> createWithExceed(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(toList());
    }

    private static MealWithExceed createWithExceed(Meal meal, boolean exceeded) {
        return new MealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), exceeded);
    }
}