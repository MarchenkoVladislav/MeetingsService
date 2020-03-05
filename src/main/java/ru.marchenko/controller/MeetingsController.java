package ru.marchenko.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.marchenko.model.entity.Meeting;
import ru.marchenko.model.entity.MeetingParticipant;
import ru.marchenko.model.entity.User;
import ru.marchenko.model.enums.MeetingStatus;
import ru.marchenko.model.enums.ParticipantRole;
import ru.marchenko.model.enums.ParticipantStatus;
import ru.marchenko.service.EmailsService;
import ru.marchenko.service.MeetingParticipantsService;
import ru.marchenko.service.MeetingsService;
import ru.marchenko.service.UsersService;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * @author Vladislav Marchenko
 */
@RestController
@RequestMapping("/meetings/")
@SessionAttributes("userID")
public class MeetingsController {
    @Autowired
    private MeetingsService meetingsService;

    @Autowired
    private MeetingParticipantsService meetingParticipantsService;

    @Autowired
    private EmailsService emailsService;

    @Autowired
    private UsersService usersService;

    @PostMapping(value = "create/")
    public Meeting createMeeting(HttpSession session, @RequestParam Date startTime, @RequestParam Date endTime, @RequestParam String description) {
        String userId = (String) session.getAttribute("userID");

        if (meetingsService.isValidMeetingInfo(startTime, endTime, description) && userId != null) {
            Meeting meeting = meetingsService.saveOrUpdateMeeting(new Meeting(startTime, endTime, description));

            User user = usersService.getUserByID(userId);

            MeetingParticipant meetingParticipant = new MeetingParticipant(meeting, user, ParticipantRole.ORGANIZER);

            meeting.getMeetingParticipants().add(meetingParticipant);

            return meetingsService.saveOrUpdateMeeting(meeting);
        }

        else {
            return null;
        }
    }

    @PutMapping(value = "cancel/{meetingID}")
    public Meeting cancelMeeting(HttpSession session, @PathVariable Long meetingID) {
        String userId = (String) session.getAttribute("userID");

        Meeting meeting = meetingsService.getMitingByID(meetingID);

        if (userId != null && meeting != null) {
            User user = usersService.getUserByID(userId);

            if (meetingParticipantsService.isMeetingOrganizer(user, meeting)) {
                for (MeetingParticipant p: meeting.getMeetingParticipants()) {
                    if (p.getParticipantRole().equals(ParticipantRole.SIMPLE_PARTICIPANT)) {
                        emailsService.send(p.getUserID().getEmail(), "Canceling of meeting",
                                "This meeting is canceled:\n" + p.getMeetingID().getDescription());
                    }
                }

                return meetingsService.changeMeetingStatus(meetingID, MeetingStatus.CANCELED);
            }
            return null;
        }
       return null;
    }

    @PutMapping(value = "addParticipant/")
    public MeetingParticipant addParticipantToMeeting(HttpSession session, @RequestParam Long meetingID, @RequestParam String userID,
                                           @RequestParam ParticipantRole participantRole) {
        String userID1 = (String) session.getAttribute("userID");

        Meeting meeting = meetingsService.getMitingByID(meetingID);

        if (userID1 != null && meeting != null) {
            User user= usersService.getUserByID(userID);
            if (!meetingParticipantsService.participantIsBisy(meeting, user)) {
                emailsService.send(user.getEmail(), "Meeting invitation", "You are invented to this meeting\n" +
                        meeting.getDescription());
                return meetingParticipantsService.addParticipantToMeeting(meeting, user, participantRole);
            }
            return null;
        }
        return null;
    }

    @DeleteMapping(value = "deleteParticipant/")
    public MeetingParticipant deleteParticipantFromMeeting(HttpSession session, @RequestParam Long participantID) {
        String userID = (String) session.getAttribute("userID");

        if (userID != null) {
            MeetingParticipant meetingParticipant = meetingParticipantsService.getParticipantByID(participantID);

            if (meetingParticipant != null) {
                meetingParticipantsService.deleteParticipantFromMeeting(meetingParticipant);
                emailsService.send(meetingParticipant.getUserID().getEmail(), "Deleting from meeting", "You was deleted" +
                        "from this meeting:\n" + meetingParticipant.getMeetingID().getDescription());
            }

            return meetingParticipant;
        }
        return null;
    }

    @DeleteMapping(value = "deleteMeeting/{meetingID}")
    public Meeting deleteMeeting(@PathVariable Long meetingID, HttpSession session) {

        String userID = (String) session.getAttribute("userID");

        if (userID != null) {
            Meeting meeting = meetingsService.getMitingByID(meetingID);
            User user = usersService.getUserByID(userID);

            if (meeting != null && meetingsService.getMeetingsByUserAndRole(user, ParticipantRole.ORGANIZER).contains(meeting)) {
                meetingsService.deleteMeeting(meeting);
                return meeting;
            }

            return null;
        }
        return null;
    }

    @GetMapping(value = "allMeetingsByUser/")
    public List<Meeting> getMeetingsByUser(@RequestParam String userID, HttpSession session) {
        String userID1 = (String) session.getAttribute("userID");

        if (userID1 != null) {
            return meetingsService.getMeetingsByUser(usersService.getUserByID(userID));
        }

        return null;
    }

    @GetMapping(value = "futureMeetingsByUser/")
    public List<Meeting> getFutureMeetingsForUser(@RequestParam String userID, HttpSession session) {
        String userID1 = (String) session.getAttribute("userID");

        if (userID1 != null) {
            return meetingsService.getFutureMeetingsForUser(usersService.getUserByID(userID));
        }

        return null;
    }

    @PutMapping("changeParticipantStatus/")
    public MeetingParticipant changeParticipantStatus(@RequestParam Long partisipantID,
                                                      @RequestParam ParticipantStatus participantStatus, HttpSession session) {
        String userID = (String) session.getAttribute("userID");

        if (userID != null) {
            MeetingParticipant meetingParticipant = meetingParticipantsService.getParticipantByID(partisipantID);

            return meetingParticipantsService.changeParticipantStatus(meetingParticipant, participantStatus);
        }

        return null;
    }
}
