package by.betrayal.scheduleservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "class_types")
@Data
public class ClassTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;
}
