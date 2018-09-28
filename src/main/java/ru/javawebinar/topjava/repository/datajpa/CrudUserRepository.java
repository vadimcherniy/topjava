package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.User;

import java.util.List;
import java.util.Optional;

public interface CrudUserRepository extends JpaRepository<User, Integer> {

    @Transactional
    @Override
    <S extends User> S save(S entity);

    @Override
    Optional<User> findById(Integer id);

    @Override
    List<User> findAll(Sort sort);

    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.id=:id")
    int delete(@Param("id") int id);

    User findByEmail(String email);
}
