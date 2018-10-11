package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping(value = "/meals")
public class JspMealController extends AbstractMealController {

    @GetMapping
    public String mealList(Model model) {
        model.addAttribute("meals", super.getAll());
        return "meals";
    }

    @GetMapping("/create")
    public String addMeal(Model model) {
        model.addAttribute("meal", new Meal(null, DateTimeUtil.getNow(), "Новая еда", 500));
        return "editMeal";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") String mealId) {
        super.delete(Integer.valueOf(mealId));
        return "redirect:/meals";
    }

    @GetMapping("/update")
    public String editForUpdate(@RequestParam("id") String mealId, Model model) {
        model.addAttribute("meal", super.get(Integer.valueOf(mealId)));
        return "editMeal";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("meal") Meal meal, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "editMeal";
        }
        mealService.update(meal, SecurityUtil.authUserId());
        return "redirect:/meals";
    }

    @PostMapping("/filter")
    public String filter(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("endDate") LocalDate endDate,
                         @RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
                         @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) @RequestParam("endTime") LocalTime endTime,
                         Model model) {
        List<Meal> mealsDateFiltered = mealService.getBetweenDates(
                startDate != null ? startDate : DateTimeUtil.MIN_DATE,
                endDate != null ? endDate : DateTimeUtil.MAX_DATE, SecurityUtil.authUserId());

        List<MealWithExceed> mealWithExceedList = MealsUtil.getFilteredWithExceeded(mealsDateFiltered,
                startTime != null ? startTime : LocalTime.MIN,
                endTime != null ? endTime : LocalTime.MAX,
                SecurityUtil.authUserCaloriesPerDay()
        );
        model.addAttribute("meals", mealWithExceedList);
        return "meals";
    }
}
