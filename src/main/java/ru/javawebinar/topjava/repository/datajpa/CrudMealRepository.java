package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Query("SELECT m FROM Meal m WHERE m.id =?1 and m.user.id =?2")
    Optional<Meal> getWithUser(Integer id, Integer userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Meal m WHERE m.id = ?1 and m.user.id=?2")
    int delete(Integer id, Integer userId);

    @Query("SELECT m FROM Meal m WHERE m.user.id=:userId ORDER BY m.dateTime DESC")
    List<Meal> getAll(@Param("userId") Integer userId);
    @Transactional

    @Override
    <S extends Meal> S save(S entity);

    @Query("SELECT m FROM Meal m WHERE m.dateTime BETWEEN ?1 AND ?2 AND m.user.id=?3 ORDER BY m.dateTime DESC")
    List<Meal> getBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, Integer userId);
}
