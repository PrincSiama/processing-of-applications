package dev.sosnovsky.applications.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "applications")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String description;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String name;

    @Column(name = "creators_id")
    private int creatorsId;

    @Enumerated(EnumType.STRING)
    private StatusOfApplications status;

    @Column(name = "create_date")
    private LocalDateTime createDate;

}
