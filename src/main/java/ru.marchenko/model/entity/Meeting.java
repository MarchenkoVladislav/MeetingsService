package ru.marchenko.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.marchenko.model.enums.MeetingStatus;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Vladislav Marchenko
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "meetings")
public class Meeting {
    @Id
    @Column(columnDefinition = "bigint", nullable = false, name = "meeting_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long meetingID;

    @Column(columnDefinition = "timestamp without time zone", nullable = false,name = "start_time")
    private Date startTime;

    @Column(columnDefinition = "timestamp without time zone", nullable = false,name = "end_time")
    private Date endTime;

    @Column(columnDefinition = "text", nullable = false,name = "description")
    private String description;

    @Column(columnDefinition = "character varying (10)", nullable = false,name = "meeting_status")
    @Enumerated(EnumType.STRING)
    private MeetingStatus meetingStatus;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "meetingID")
    private Set<MeetingParticipant> meetingParticipants = new HashSet<>();

    public Meeting(Date startTime, Date endTime, String description) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.meetingStatus = MeetingStatus.EXPECTED;
    }
}
