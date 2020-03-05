package ru.marchenko.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;
import ru.marchenko.model.entity.Meeting;
import ru.marchenko.model.entity.MeetingParticipant;
import ru.marchenko.model.entity.User;
import ru.marchenko.model.enums.MeetingStatus;
import ru.marchenko.model.enums.ParticipantRole;
import ru.marchenko.model.repository.MeetingParticipantsRepo;
import ru.marchenko.model.repository.MeetingsRepo;
import ru.marchenko.model.repository.UsersRepo;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Vladislav Marchenko
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MeetingsServiceTest {

    @MockBean
    private EmailsService emailsService;

    @MockBean
    private MeetingParticipantsService meetingParticipantsService;

    @Autowired
    private MeetingsService  meetingsService;

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private MeetingsRepo meetingsRepo;

    @Autowired
    private MeetingParticipantsRepo meetingParticipantsRepo;

    @Test(expected = DataIntegrityViolationException.class)
    public void cancelMeeting() throws Exception {

        usersRepo.deleteById("1");
        usersRepo.deleteById("2");
        usersRepo.deleteById("3");

        List<User> users = usersRepo.saveAll(Arrays.asList(
                new User("1","Name", "vlad25091999@mail.ru"),
                new User("2","Name1", "vla@mail.ru"),
                new User("3","Name2", "vlad@mail.ru")
        ));

        DateFormat df = new SimpleDateFormat();

        Date start = df.parse("10.05.20 10:00");

        Date end = df.parse("10.05.20 12:00");

        Meeting meeting = new Meeting(start, end, "descr");

        List<MeetingParticipant> meetingParticipants = Arrays.asList(
                new MeetingParticipant(meeting, users.get(0), ParticipantRole.ORGANIZER),
                new MeetingParticipant(meeting, users.get(1), ParticipantRole.SIMPLE_PARTICIPANT),
                new MeetingParticipant(meeting, users.get(2), ParticipantRole.SIMPLE_PARTICIPANT)
        );

        meeting.getMeetingParticipants().addAll(meetingParticipants);

        meetingsRepo.save(meeting);

        User user = usersRepo.findUserByEmail("vlad25091999@mail.ru");

        usersRepo.deleteById("1");
        usersRepo.deleteById("2");
        usersRepo.deleteById("3");

        Assert.assertEquals(meeting.getMeetingStatus(), MeetingStatus.CANCELED);

        Mockito.verify(emailsService, Mockito.times(2))
                .send(
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString()
                );
    }
}