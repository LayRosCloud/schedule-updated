package by.betrayal.scheduleservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "class_types")
@Data
public class TypeClassEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "institution_id", nullable = false)
    private Long institutionId;
}
