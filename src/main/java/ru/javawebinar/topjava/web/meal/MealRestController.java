package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
public class MealRestController {

    private static final Logger LOG = getLogger(MealRestController.class);

    @Autowired
    private MealService mealService;

    public Meal save(Meal meal) {
        return mealService.save(meal);
    }

    public void delete(Long id) {
        mealService.delete(id, SecurityUtil.authUserId());
    }

    public Meal get(Long id) {
        return mealService.get(id, SecurityUtil.authUserId());
    }

    public List getAll() {
        return mealService.getAll(SecurityUtil.authUserId());
    }

}
