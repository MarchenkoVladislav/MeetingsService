package ru.marchenko.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.marchenko.model.entity.Meeting;

/**
 * @author Vladislav Marchenko
 */
@Repository
public interface MeetingsRepo extends JpaRepository<Meeting, Long> {
}
