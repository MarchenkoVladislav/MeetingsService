package ru.marchenko.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${meeting.valid.descr.length.max}")
    private int validDescrLenMax;

    @Value("${meeting.valid.descr.length.min}")
    private int validDescrLenMin;

    @Value("${meeting.valid.daysinterval}")
    private int validDaysInterval;

    @Value("${millisec.into.days}")
    private int millisecIntoDaysKoef;

    public Meeting saveOrUpdateMeeting(Meeting meeting) {
        return meetingsRepo.save(meeting);
    }

    public Meeting getMitingByID(Long id) {
        return meetingsRepo.getOne(id);
    }

    public Meeting changeMeetingStatus(Long meetingID, MeetingStatus meetingStatus) {
        Meeting meeting1 = meetingsRepo.getOne(meetingID);
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

    public List<Meeting> getMeetingsByDate(Date date) {
        return meetingsRepo.findMeetingsByDate(date);
    }

    public boolean isValidMeetingInfo(Date startTime, Date endTime, String description) {
        int daysInt = (int)(endTime.getTime() - startTime.getTime()) / millisecIntoDaysKoef;

        if (startTime.after(endTime) || (daysInt > validDaysInterval)
                || (description.length() > validDescrLenMax) || (description.length() < validDescrLenMin)) {
            return false;
        }

        return  true;
    }
}
