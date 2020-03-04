package ru.marchenko.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Vladislav Marchenko
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "users", schema = "meetings")
public class User {
    @Id
    @Column(columnDefinition = "character varying (255)", nullable = false, name = "user_id")
    private String userID;

    @Column(columnDefinition = "character varying (100)", nullable = false,name = "user_name")
    private String userName;

    @Column(columnDefinition = "character varying (50)", nullable = false,name = "email")
    private String email;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "userID")
    private Set<MeetingParticipant> meetingParticipants = new HashSet<>();

    public User(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }
}
