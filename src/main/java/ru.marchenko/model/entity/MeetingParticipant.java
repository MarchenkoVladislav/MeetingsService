package ru.marchenko.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.marchenko.model.enums.ParticipantRole;
import ru.marchenko.model.enums.ParticipantStatus;

import javax.persistence.*;

/**
 * @author Vladislav Marchenko
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "meetings_participants", schema = "meetings")
public class MeetingParticipant {
    @Id
    @Column(columnDefinition = "bigint", nullable = false, name = "participant_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long participantID;

    @ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(columnDefinition = "bigint", nullable = false, name = "meeting_id")
    private Meeting meetingID;

    @ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(columnDefinition = "character varying (255)", nullable = false, name = "user_id")
    private User userID;

    @Column(columnDefinition = "character varying (20)", nullable = false,name = "participant_status")
    private ParticipantStatus participantStatus;

    @Column(columnDefinition = "character varying (20)", nullable = false,name = "participant_role")
    private ParticipantRole participantRole;

    public MeetingParticipant(Meeting meetingID, User userID, ParticipantRole participantRole) {
        this.meetingID = meetingID;
        this.userID = userID;
        this.participantStatus = ParticipantStatus.DOES_NOT_AGREES;
        this.participantRole = participantRole;
    }
}
