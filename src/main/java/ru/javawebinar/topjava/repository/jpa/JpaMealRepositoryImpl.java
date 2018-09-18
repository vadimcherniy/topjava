package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Repository("jpaMealRepositoryImpl")
public class JpaMealRepositoryImpl implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, Integer userId) {
        return null;
    }

    @Override
    @Transactional
    public boolean delete(Integer id, Integer userId) {
        return false;
    }

    @Override
    public Meal get(Integer id, Integer userId) {
        return null;
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, Integer userId) {
        return null;
    }

    @Override
    public List getAll(Integer userId) {
        return null;
    }
}
