package ru.marchenko.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.marchenko.model.entity.MeetingParticipant;

/**
 * @author Vladislav Marchenko
 */
@Repository
public interface MeetingParticipantsRepo extends JpaRepository<MeetingParticipant, Long> {
}
