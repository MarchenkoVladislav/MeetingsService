package ru.marchenko.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.marchenko.model.enums.MeetingStatus;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Vladislav Marchenko
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "meetings", schema = "meetings")
public class Meeting {
    @Id
    @Column(columnDefinition = "bigint", nullable = false, name = "meeting_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long meetingID;

    @Column(columnDefinition = "timestamp without time zone", nullable = false,name = "date")
    private Date date;

    @Column(columnDefinition = "text", nullable = false,name = "description")
    private String description;

    @Column(columnDefinition = "character varying (10)", nullable = false,name = "meeting_status")
    private MeetingStatus meetingStatus;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "meetingID")
    private Set<MeetingParticipant> meetingParticipants = new HashSet<>();

    public Meeting(Date date, String description, MeetingStatus meetingStatus) {
        this.date = date;
        this.description = description;
        this.meetingStatus = meetingStatus;
    }
}
