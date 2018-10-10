package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/meals")
public class MealController {

    private MealService mealService;

    @Autowired
    public void setMealService(MealService mealService) {
        this.mealService = mealService;
    }

    @GetMapping
    public String meals(Model model) {
        List meals = mealWithExceeds(mealService.getAll(SecurityUtil.authUserId()));
        model.addAttribute("meals", meals);
        return "meals";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("meal", new Meal(null, DateTimeUtil.getNow(), "Новая еда", 500));
        return "editMeal";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") String mealId) {
        mealService.delete(Integer.valueOf(mealId), SecurityUtil.authUserId());
        return "redirect:/meals";
    }

    @GetMapping("/update")
    public String update(@RequestParam("id") String mealId, Model model) {
        Meal meal = mealService.get(Integer.valueOf(mealId), SecurityUtil.authUserId());
        model.addAttribute("meal", meal);
        return "editMeal";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("meal") Meal meal, BindingResult bindingResult) {
        mealService.update(meal, SecurityUtil.authUserId());
        return "redirect:/meals";
    }

    @PostMapping("/filter")
    public String filter(@RequestParam("startDate") LocalDate startDate, @RequestParam("endDate") LocalDate endDate,
                         @RequestParam("startTime") LocalTime startTime, @RequestParam("endTime") LocalTime endTime) {
        return "meals";
    }

    private List<MealWithExceed> mealWithExceeds(List<Meal> meals) {
        return MealsUtil.getFilteredWithExceeded(meals, LocalTime.MIN, LocalTime.MAX, MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }
}
