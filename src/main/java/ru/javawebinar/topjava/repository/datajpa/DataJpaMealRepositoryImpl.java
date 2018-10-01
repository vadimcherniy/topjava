package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DataJpaMealRepositoryImpl implements MealRepository {

    private CrudMealRepository crudMealRepository;
    private CrudUserRepository crudUserRepository;

    @Autowired
    public void setCrudMealRepository(CrudMealRepository crudMealRepository) {
        this.crudMealRepository = crudMealRepository;
    }

    @Autowired
    public void setCrudUserRepository(CrudUserRepository crudUserRepository) {
        this.crudUserRepository = crudUserRepository;
    }

    @Override
    public Meal save(Meal meal, Integer userId) {
        if (!meal.isNew() && get(meal.getId(), userId) == null) {
            return null;
        }
        meal.setUser(crudUserRepository.getOne(userId));
        return crudMealRepository.save(meal);
    }

    @Override
    public boolean delete(Integer id, Integer userId) {
        return crudMealRepository.delete(id, userId) != 0;
    }

    @Override
    public Meal get(Integer id, Integer userId) {
        return crudMealRepository.get(id, userId).orElse(null);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, Integer userId) {
        return crudMealRepository.getBetween(startDateTime, endDateTime, userId);
    }

    @Override
    public List getAll(Integer userId) {
        return crudMealRepository.getAll(userId);
    }

    @Override
    public Meal getWithUser(Integer id, Integer userId) {
        return crudMealRepository.getWithUser(id, userId).orElse(null);
    }
}
