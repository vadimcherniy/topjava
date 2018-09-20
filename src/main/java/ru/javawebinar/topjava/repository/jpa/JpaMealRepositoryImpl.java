package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
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
        User user = em.getReference(User.class, userId);
        meal.setUser(user);
        if (meal.isNew()) {
            em.persist(meal);
        } else {
            em.merge(meal);
        }
        return meal;
    }

    @Override
    @Transactional
    public boolean delete(Integer id, Integer userId) {
        return em.createNamedQuery(Meal.DELETE)
                .setParameter("id", id)
                .setParameter("userId", userId)
                .executeUpdate() != 0;
    }

    @Override
    public Meal get(Integer id, Integer userId) {
        return em.createNamedQuery(Meal.GET, Meal.class)
                .setParameter("id", id)
                .setParameter("userId", userId)
                .getSingleResult();
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, Integer userId) {
        return em.createNamedQuery(Meal.GET_BETWEEN, Meal.class)
                .setParameter("userId", userId)
                .setParameter("startDateTime", startDateTime)
                .setParameter("endDateTime", endDateTime)
                .getResultList();
    }

    @Override
    public List getAll(Integer userId) {
        return em.createNamedQuery(Meal.GET_ALL, Meal.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}
