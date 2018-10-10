package ru.javawebinar.topjava.model;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;
import ru.javawebinar.topjava.util.DateTimeUtil;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "meals", uniqueConstraints = {@UniqueConstraint(columnNames = {"date_time", "user_id"}, name = "meals_unique_user_datetime_idx")})
@NamedQueries(value = {
        @NamedQuery(name = Meal.GET, query = "SELECT m FROM Meal m WHERE m.id=:id AND m.user.id=:userId ORDER BY m.dateTime DESC"),
        @NamedQuery(name = Meal.GET_ALL, query = "SELECT m FROM Meal m WHERE m.user.id=:userId ORDER BY m.dateTime DESC"),
        @NamedQuery(name = Meal.DELETE, query = "DELETE FROM Meal m WHERE m.id=:id AND m.user.id=:userId"),
        @NamedQuery(name = Meal.GET_BETWEEN, query = "SELECT m FROM Meal m WHERE m.user.id=:userId " +
                "AND (m.dateTime BETWEEN :startDateTime AND :endDateTime) ORDER BY m.dateTime DESC"),
})
public class Meal extends AbstractBaseEntity {

    public static final String GET = "Meal.get";
    public static final String GET_ALL = "Meal.getAll";
    public static final String DELETE = "Meal.delete";
    public static final String GET_BETWEEN = "Meal.getBetween";

    @NotNull
    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN)
    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @NotBlank
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Range(min = 10, max = 5000)
    @Column(name = "calories", nullable = false)
    private Integer calories;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Meal() {}

    public Meal(Meal m) {
        this(m.id, m.dateTime, m.description, m.calories);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, Integer calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
