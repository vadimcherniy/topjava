package ru.javawebinar.topjava.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(MealServlet.class);

    private MealRestController controller;

    @Override
    public void init() throws ServletException {
        super.init();
        controller = new ClassPathXmlApplicationContext("spring/spring-app.xml").getBean(MealRestController.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("redirect to user meals");

        String action = req.getParameter("action");
        String id = req.getParameter("id");

        switch (action == null ? "all" : action) {
            case ("delete"):
                controller.delete(Long.valueOf(id));
                resp.sendRedirect("meals");
                break;
            case ("update"):
            case ("create"):
                Meal meal = id != null ? controller.get(Long.valueOf(id)) : new Meal(null, SecurityUtil.authUserId(), LocalDateTime.now(), "", 1000);
                req.setAttribute("meal", meal);
                req.getRequestDispatcher("/mealForm.jsp").forward(req, resp);
                break;
            case ("all"):
            default:
                List mealWithExceeds = MealsUtil.getFilteredWithExceeded(controller.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);
                req.setAttribute("meals", mealWithExceeds);
                req.getRequestDispatcher("/meals.jsp").forward(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String id = req.getParameter("id");

        Meal meal = new Meal(id.isEmpty() ? null : Long.valueOf(id),
                SecurityUtil.authUserId(),
                LocalDateTime.parse(req.getParameter("dateTime")),
                req.getParameter("description"),
                Integer.parseInt(req.getParameter("calories")));

        LOG.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        controller.save(meal);
        resp.sendRedirect("meals");
    }
}
