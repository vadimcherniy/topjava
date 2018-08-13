package ru.javawebinar.topjava.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("redirect to user meals");

        List<MealWithExceed> mealWithExceeds =
                MealsUtil.getFilteredWithExceeded(MealsUtil.getMealList(), LocalTime.MIN, LocalTime.MAX, 2000);
        req.setAttribute("meals", mealWithExceeds);

        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
    }

}
