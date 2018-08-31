package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
public class MealRestController {

    private static final Logger LOG = getLogger(MealRestController.class);

    private MealService mealService;

    @Autowired
    public void setMealService(MealService mealService) {
        this.mealService = mealService;
    }

    public Meal create(Meal meal) {
        return mealService.crete(meal, SecurityUtil.authUserId());
    }

    public Meal update(Meal meal) {
        return mealService.update(meal, SecurityUtil.authUserId());
    }

    public void delete(Integer id) {
        mealService.delete(id, SecurityUtil.authUserId());
    }

    public Meal get(Integer id) {
        return mealService.get(id, SecurityUtil.authUserId());
    }

    public List getAll() {
        return mealService.getAll(SecurityUtil.authUserId());
    }

    public List<MealWithExceed> getBetween(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        List<Meal> mealsDateFiltered = mealService.getBetweenDates(
                startDate != null ? startDate : DateTimeUtil.MIN_DATE,
                endDate != null ? endDate : DateTimeUtil.MAX_DATE, SecurityUtil.authUserId());

        return MealsUtil.getFilteredWithExceeded(mealsDateFiltered,
                startTime != null ? startTime : LocalTime.MIN,
                endTime != null ? endTime : LocalTime.MAX,
                SecurityUtil.authUserCaloriesPerDay()
        );
    }
}
