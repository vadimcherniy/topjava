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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(MealServlet.class);

    private MealRestController controller;
    private ClassPathXmlApplicationContext context;

    @Override
    public void init() throws ServletException {
        super.init();
        context = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        controller = context.getBean(MealRestController.class);
    }

    @Override
    public void destroy() {
        context.close();
        super.destroy();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        String id = request.getParameter("id");

        switch (action == null ? "all" : action) {
            case ("delete"):
                controller.delete(Long.valueOf(id));
                response.sendRedirect("meals");
                break;
            case ("update"):
            case ("create"):
                Meal meal = id != null ? controller.get(Long.valueOf(id)) : new Meal(null, SecurityUtil.authUserId(), LocalDateTime.now(), "", 1000);
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case ("all"):
            default:
                List mealWithExceeds = MealsUtil.getFilteredWithExceeded(controller.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);
                request.setAttribute("meals", mealWithExceeds);
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) {
            String id = request.getParameter("id");

            Meal meal = new Meal(id.isEmpty() ? null : Long.valueOf(id),
                    SecurityUtil.authUserId(),
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.parseInt(request.getParameter("calories")));

            if (request.getParameter("id").isEmpty()) {
                controller.create(meal);
            } else {
                controller.update(meal);
            }
        } else if ("filter".equals(action)) {
            LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
            LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
            LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
            LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
            request.setAttribute("meals", controller.getBetween(startDate, startTime, endDate, endTime));
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }
        response.sendRedirect("meals");
    }
}
