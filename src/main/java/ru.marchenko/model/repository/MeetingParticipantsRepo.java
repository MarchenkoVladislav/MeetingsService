package ru.marchenko.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.marchenko.model.entity.Meeting;
import ru.marchenko.model.entity.MeetingParticipant;
import ru.marchenko.model.entity.User;
import ru.marchenko.model.enums.ParticipantStatus;

import java.util.List;

/**
 * @author Vladislav Marchenko
 */
@Repository
public interface MeetingParticipantsRepo extends JpaRepository<MeetingParticipant, Long> {
    List<MeetingParticipant> findMeetingParticipantsByUserIDAndMeetingID(User userID, Meeting meetingID);

    List<MeetingParticipant> findMeetingParticipantsByUserIDAndParticipantStatus(User userID, ParticipantStatus participantStatus);
}
