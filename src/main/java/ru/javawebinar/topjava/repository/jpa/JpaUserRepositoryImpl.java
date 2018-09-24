package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository("jpaUserRepositoryImpl")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class JpaUserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public User save(User user) {
        if (user.isNew()) {
           em.persist(user);
        } else {
            em.merge(user);
        }
        return user;
    }

    @Override
    @Transactional
    public boolean delete(Integer id) {
        return em.createNamedQuery(User.DELETE).setParameter("id", id).executeUpdate() != 0;
    }

    @Override
    public User get(Integer id) {
        return em.find(User.class, id);
    }

    @Override
    public User getByEmail(String email) {
        try {
            return em.createNamedQuery(User.GET_BY_EMAIL, User.class).setParameter("email", email).getSingleResult();
        } catch (NoResultException e) {
            throw new NotFoundException("Not found user with email = " + email);
        }
    }

    @Override
    public List<User> getAll() {
        return em.createNamedQuery(User.GET_ALL).getResultList();
    }
}
