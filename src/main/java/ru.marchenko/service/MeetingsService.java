package ru.marchenko.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.marchenko.model.entity.Meeting;
import ru.marchenko.model.entity.User;
import ru.marchenko.model.enums.MeetingStatus;
import ru.marchenko.model.enums.ParticipantRole;
import ru.marchenko.model.enums.ParticipantStatus;
import ru.marchenko.model.repository.MeetingsRepo;

import java.util.Date;
import java.util.List;

/**
 * @author Vladislav Marchenko
 */
@Service
public class MeetingsService {

    @Autowired
    private MeetingsRepo meetingsRepo;

    public Meeting saveOrUpdateMeeting(Date startTime,Date endTime, String description) {
        return meetingsRepo.save(new Meeting(startTime, endTime, description));
    }

    public Meeting changeMeetingStatus(Meeting meeting, MeetingStatus meetingStatus) {
        Meeting meeting1 = meetingsRepo.getOne(meeting.getMeetingID());
        meeting1.setMeetingStatus(meetingStatus);
        return meetingsRepo.save(meeting1);
    }

    public void deleteMeeting(Meeting meeting) {
        meetingsRepo.delete(meeting);
    }

    public Meeting changeMeetingInfo(Meeting meeting, Date date, String  description) {
        Meeting meeting1 = meetingsRepo.getOne(meeting.getMeetingID());
        meeting1.setStartTime(date);
        meeting1.setDescription(description);
        return meetingsRepo.save(meeting1);
    }

    public List<Meeting> getMeetingsByUser(User user) {
        return meetingsRepo.findMeetingsByUser(user);
    }

    public List<Meeting> getMeetingsByUserAndRole(User user, ParticipantRole participantRole) {
        return meetingsRepo.findMeetingsByUserAndRole(user, participantRole);
    }

    public List<Meeting> getMeetingsByUserAndStatus(User user, ParticipantStatus participantStatus) {
        return meetingsRepo.findMeetingsByUserAndStatus(user, participantStatus);
    }

    public List<Meeting> getMeetingsByUserAndRoleAndStatus(User user, ParticipantRole participantRole, ParticipantStatus participantStatus) {
        return meetingsRepo.findMeetingsByUserAndRoleAndStatus(user, participantRole, participantStatus);
    }

    public List<Meeting> getMeetingsByTwoUsers(User user1, User user2) {
        return meetingsRepo.findMeetingsByTwoUsers(user1, user2);
    }
}
