package dev.sosnovsky.applications.model;

public enum Role {
    USER,
    OPERATOR,
    ADMINISTRATOR

/*@Data
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "role")
    private String name;*/
}
