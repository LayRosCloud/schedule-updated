package by.betrayal.audienceservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "audiences_types")
@Data
public class AudienceTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    @Column(name = "name", nullable = false)
    private String name;
}
