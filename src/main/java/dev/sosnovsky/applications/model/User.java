package dev.sosnovsky.applications.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String password;

    /*@Enumerated(EnumType.STRING)
    @CollectionTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"))
//    @Column(name = "role_id")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> role;*/
    private String role;
}
