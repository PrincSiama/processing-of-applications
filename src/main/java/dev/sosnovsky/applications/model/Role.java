package dev.sosnovsky.applications.model;

import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "role")
    private String name;
}
