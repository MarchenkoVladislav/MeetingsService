package ru.marchenko.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Vladislav Marchenko
 */
@Data
@Entity
@Table(name = "users", schema = "meetings")
public class User {
    @Id
    @Column(columnDefinition = "bigint", nullable = false, name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userID;

    @Column(columnDefinition = "character varying (50)", nullable = false,name = "first_name")
    private String firstName;

    @Column(columnDefinition = "character varying (50)", nullable = false,name = "last_name")
    private String lastName;

    @Column(columnDefinition = "character varying (50)", nullable = false,name = "email")
    private String email;

    @Column(columnDefinition = "character varying (50)", nullable = false,name = "password")
    private String password;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "userID")
    private Set<MeetingParticipant> meetingParticipants = new HashSet<>();

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
}
