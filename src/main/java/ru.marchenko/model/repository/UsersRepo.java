package ru.marchenko.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.marchenko.model.entity.User;

/**
 * @author Vladislav Marchenko
 */
@Repository
public interface UsersRepo extends JpaRepository<User, String> {
    User findUserByEmail(String email);
}
