package ru.marchenko.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.marchenko.model.entity.Meeting;
import ru.marchenko.model.entity.MeetingParticipant;
import ru.marchenko.model.repository.MeetingsRepo;

import java.util.Date;
import java.util.List;

/**
 * @author Vladislav Marchenko
 */
@Service
public class EmailsService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MeetingsRepo meetingsRepo;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${meeting.notification.time.min}")
    private long minNotificationTime;

    @Value("${meeting.valid.descr.length.max}")
    private long maxNotificationTime;

    public void send(String emailTo, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(username);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }

    @Scheduled(fixedRate = 60000)
    public void scheduledSend() {
        Date curDate = new Date();

        List<Meeting> meetings = meetingsRepo.findMeetingsByDate(curDate);

        for(Meeting m: meetings) {
            long deltaTime = m.getStartTime().getTime() - curDate.getTime();
            if (deltaTime >= minNotificationTime && deltaTime <= maxNotificationTime) {
                for (MeetingParticipant mp: m.getMeetingParticipants()) {
                    send(mp.getUserID().getEmail(), "Meeting notification", "This meeting will be though 15 min:\n" +
                            m.getDescription());
                }
            }
        }
    }
}
