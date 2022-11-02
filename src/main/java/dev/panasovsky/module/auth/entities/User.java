package dev.panasovsky.module.auth.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String login;
    private String password;
    private Date created_date;
    private Date last_session;

    @ManyToOne
    @JoinColumn(name = "user_role")
    private Role user_role;

}