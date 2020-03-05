package ru.marchenko.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.marchenko.model.entity.Meeting;
import ru.marchenko.model.entity.User;
import ru.marchenko.model.enums.ParticipantRole;

import java.util.List;

/**
 * @author Vladislav Marchenko
 */
@Repository
public interface MeetingsRepo extends JpaRepository<Meeting, Long> {
    @Query(value = "select * from meetings join meetings_participants on meetings_participants.meeting_id = meetings.meeting_id " +
            "where meetings_participants.user_id = :user", nativeQuery = true)
    List<Meeting> findMeetingsByUser(User user);

    @Query(value = "select * from meetings join meetings_participants on meetings_participants.meeting_id = meetings.meeting_id " +
            "where meetings_participants.user_id = :user and meetings.meeting_status='EXPECTED'", nativeQuery = true)
    List<Meeting> findFutureMeetingsForUser(User user);

    @Query(value = "select * from meetings join meetings_participants on meetings_participants.meeting_id = meetings.meeting_id" +
            "where meetings_participants.user_id = :user " +
            "and meetings_participants.participant_role = :participantRole", nativeQuery = true)
    List<Meeting> findMeetingsByUserAndRole(User user, ParticipantRole participantRole);


    @Query(value = "select * from meetings where date_part('year', meetings.start_time) = date_part('year', now()) " +
            "and date_part('month', meetings.start_time) = date_part('month', now()) " +
            "and date_part('day', meetings.start_time) = date_part('day', now())", nativeQuery = true)
    List<Meeting> findMeetingsByDate();
}
