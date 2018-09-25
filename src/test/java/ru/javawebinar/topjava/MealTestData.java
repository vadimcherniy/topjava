package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static java.time.LocalDateTime.of;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final Integer ADMIN_ID = START_SEQ;
    public static final Integer USER_ID = START_SEQ + 1;

    public static final Integer ADMIN_MEAL_1_ID = START_SEQ + 2;
    public static final Integer ADMIN_MEAL_2_ID = START_SEQ + 3;
    public static final Integer MEAL_1_ID = START_SEQ + 4;
    public static final Integer MEAL_2_ID = START_SEQ + 5;
    public static final Integer MEAL_3_ID = START_SEQ + 6;
    public static final Integer MEAL_4_ID = START_SEQ + 7;
    public static final Integer MEAL_5_ID = START_SEQ + 8;
    public static final Integer MEAL_6_ID = START_SEQ + 9;

    public static final Meal MEAL_1 = new Meal(MEAL_1_ID, LocalDateTime.of(2018, Month.AUGUST, 31, 9, 0), "Завтрак", 500);
    public static final Meal MEAL_2 = new Meal(MEAL_2_ID, LocalDateTime.of(2018, Month.AUGUST, 31, 14, 0), "Обед", 1000);
    public static final Meal MEAL_3 = new Meal(MEAL_3_ID, LocalDateTime.of(2018, Month.AUGUST, 31, 19, 0), "Ужин", 500);
    public static final Meal MEAL_4 = new Meal(MEAL_4_ID, LocalDateTime.of(2018, Month.SEPTEMBER, 1, 10, 0), "Завтрак", 500);
    public static final Meal MEAL_5 = new Meal(MEAL_5_ID, LocalDateTime.of(2018, Month.SEPTEMBER, 1, 13, 0), "Обед", 1000);
    public static final Meal MEAL_6 = new Meal(MEAL_6_ID, LocalDateTime.of(2018, Month.SEPTEMBER, 1, 20, 0), "Ужин", 510);
    public static final Meal ADMIN_MEAL_1 = new Meal(ADMIN_MEAL_1_ID, LocalDateTime.of(2018, Month.SEPTEMBER, 1, 9, 0), "Админ завтрак", 500);
    public static final Meal ADMIN_MEAL_2 = new Meal(ADMIN_MEAL_2_ID, LocalDateTime.of(2018, Month.SEPTEMBER, 1, 19, 0), "Админ ужин", 1000);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "user");
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("user").isEqualTo(expected);
    }

    public static Meal getUpdated() {
        return new Meal(MEAL_1_ID, MEAL_1.getDateTime(), "Обновленный завтрак", 200);
    }

    public static Meal getCreated() {
        return new Meal(null, of(2018, Month.SEPTEMBER, 2, 18, 0), "Созданный ужин", 400);
    }

}
