package dev.sosnovsky.applications.model;

import jakarta.persistence.Entity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Application {
    private int id;

    private String description;

    private String phoneNumber;

    private String name;

    private int creatorsId;

    private StatusOfApplications status;

    private LocalDateTime createDate;

}
