package by.betrayal.personalservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "people")
@Data
public class PersonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lastname", nullable = false)
    private String lastName;

    @Column(name = "firstname", nullable = false)
    private String firstName;

    @Column(name = "patronymic", nullable = true)
    private String patronymic;

    @Column(name = "photo", nullable = true)
    private String photo;

}
