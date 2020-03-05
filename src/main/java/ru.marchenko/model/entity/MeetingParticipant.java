package ru.marchenko.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.marchenko.model.enums.ParticipantRole;
import ru.marchenko.model.enums.ParticipantStatus;

import javax.persistence.*;

/**
 * @author Vladislav Marchenko
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "meetings_participants")
public class MeetingParticipant {
    @Id
    @Column(columnDefinition = "bigint", nullable = false, name = "participant_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long participantID;

    @ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(columnDefinition = "bigint", nullable = false, name = "meeting_id")
    @JsonIgnore
    private Meeting meetingID;

    @ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(columnDefinition = "character varying (255)", nullable = false, name = "user_id")
    @JsonIgnore
    private User userID;

    @Column(columnDefinition = "character varying (20)", nullable = false,name = "participant_status")
    @Enumerated(EnumType.STRING)
    private ParticipantStatus participantStatus;

    @Column(columnDefinition = "character varying (20)", nullable = false,name = "participant_role")
    @Enumerated(EnumType.STRING)
    private ParticipantRole participantRole;

    public MeetingParticipant(Meeting meetingID, User userID, ParticipantRole participantRole) {
        this.meetingID = meetingID;
        this.userID = userID;
        this.participantStatus = (participantRole.toString().equals("ORGANIZER")) ? ParticipantStatus.AGREES
                : ParticipantStatus.DOES_NOT_AGREES;
        this.participantRole = participantRole;
    }
}
