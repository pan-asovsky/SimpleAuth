package dev.panasovsky.module.auth.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private UUID id;

    private String login;
    private String password;

    @ManyToOne
    @JoinColumn(name = "user_role")
    private Role user_role;

}