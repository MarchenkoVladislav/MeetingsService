package ru.marchenko.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Vladislav Marchenko
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "users")
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

    public User(String id, String userName, String email) {
        this.userID = id;
        this.userName = userName;
        this.email = email;
    }
}
