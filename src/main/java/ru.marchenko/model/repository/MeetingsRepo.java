package ru.marchenko.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.marchenko.model.entity.Meeting;
import ru.marchenko.model.entity.User;
import ru.marchenko.model.enums.ParticipantRole;
import ru.marchenko.model.enums.ParticipantStatus;

import java.util.Date;
import java.util.List;

/**
 * @author Vladislav Marchenko
 */
@Repository
public interface MeetingsRepo extends JpaRepository<Meeting, Long> {
    @Query(value = "select * from meetings join meetings_participants on meetings_participants.meeting_id = meetings.meeting_id " +
            "where meetings_participants.user_id = :user", nativeQuery = true)
    List<Meeting> findMeetingsByUser(User user);

    @Query(value = "select * from meetings join meetings_participants on meetings_participants.meeting_id = meetings.meeting_id" +
            "where meetings_participants.user_id = :user " +
            "and meetings_participants.participant_role = :participantRole", nativeQuery = true)
    List<Meeting> findMeetingsByUserAndRole(User user, ParticipantRole participantRole);

    @Query(value = "select * from meetings join meetings_participants on meetings_participants.meeting_id = meetings.meeting_id" +
            " where meetings_participants.user_id = :user " +
            "and meetings_participants.participant_status = :participantStatus", nativeQuery = true)
    List<Meeting> findMeetingsByUserAndStatus(User user, ParticipantStatus participantStatus);

    @Query(value = "select * from meetings m join meetings_participants on meetings_participants.meeting_id = meetings.meeting_id" +
            " where meetings_participants.user_id = :user " +
            "and meetings_participants.participant_role = :participantRole " +
            "and meetings_participants.participant_status = :participantStatus", nativeQuery = true)
    List<Meeting> findMeetingsByUserAndRoleAndStatus(User user, ParticipantRole participantRole, ParticipantStatus participantStatus);

    @Query(value = "select * from meetings join meetings_participants on meetings_participants.meeting_id = meetings.meeting_id " +
            "where meetings_participants.user_id = :user1 " +
            "intersect " +
            "select * from meetings join meetings_participants on meetings_participants.meeting_id = meetings.meeting_id " +
            "where meetings_participants.user_id = :user2", nativeQuery = true)
    List<Meeting> findMeetingsByTwoUsers(User user1, User user2);

    @Query(value = "select * from meetings where year(meetings.start_time) = year(:date) " +
            "and month(meetings.start_time) = month(:date) and day(meetings.start_time) = day(:date)", nativeQuery = true)
    List<Meeting> findMeetingsByDate(Date date);
}
