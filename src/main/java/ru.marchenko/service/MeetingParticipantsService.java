package ru.marchenko.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.marchenko.model.entity.Meeting;
import ru.marchenko.model.entity.MeetingParticipant;
import ru.marchenko.model.entity.User;
import ru.marchenko.model.enums.ParticipantRole;
import ru.marchenko.model.enums.ParticipantStatus;
import ru.marchenko.model.repository.MeetingParticipantsRepo;

import java.util.Date;
import java.util.List;

/**
 * @author Vladislav Marchenko
 */
@Service
public class MeetingParticipantsService {

    @Autowired
    private MeetingParticipantsRepo meetingParticipantsRepo;

    public MeetingParticipant addParticipantToMeeting(Meeting meetingID, User userID, ParticipantRole participantRole) {
        return meetingParticipantsRepo.save(new MeetingParticipant(meetingID, userID, participantRole));
    }

    public void deleteParticipantFromMeeting(MeetingParticipant meetingParticipant) {
        meetingParticipantsRepo.delete(meetingParticipant);
    }

    public void changeParticipantStatus(MeetingParticipant meetingParticipant, ParticipantStatus participantStatus) {
        MeetingParticipant meetingParticipant1 = meetingParticipantsRepo.getOne(meetingParticipant.getParticipantID());
        meetingParticipant1.setParticipantStatus(participantStatus);
        meetingParticipantsRepo.save(meetingParticipant1);
    }

    public MeetingParticipant getParticipantByID(Long participantID) {
        return meetingParticipantsRepo.getOne(participantID);
    }

    public boolean participantIsBisy(Meeting meeting, User user) {
        List<MeetingParticipant> meetingParticipants = meetingParticipantsRepo.findMeetingParticipantsByUserIDAndParticipantStatus(
                user, ParticipantStatus.AGREES
        );

        for (MeetingParticipant m : meetingParticipants) {
            Meeting meeting1 = m.getMeetingID();

            Date curStartTime = meeting1.getStartTime();
            Date curEndTime = meeting1.getEndTime();

            Date cmpStartTime = meeting.getStartTime();
            Date cmpEndTime = meeting.getEndTime();

            if((curStartTime.after(cmpStartTime) && curStartTime.before(cmpEndTime))
                    || (curEndTime.after(cmpStartTime) && curEndTime.before(cmpEndTime))) {
                return true;
            }
        }

        return false;
    }

    public boolean isMeetingOrganizer(User user, Meeting meeting) {
        List<MeetingParticipant> meetingParticipants = meetingParticipantsRepo.findMeetingParticipantsByUserIDAndMeetingID(user, meeting);

        if (meetingParticipants.isEmpty()) {
            return false;
        }

        else {
            MeetingParticipant meetingParticipant = meetingParticipants.get(1);
            if (meetingParticipant.getParticipantRole().toString().equals("ORGANIZER")) {
                return true;
            }
            return false;
        }
    }
}
