package by.betrayal.audienceservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "housings")
@Data
public class CorpusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @ManyToOne
    @JoinColumn(name = "institution_id", nullable = false)
    private InstitutionEntity institution;

    @Column(name = "deleted_at", nullable = true)
    private Long deletedAt;
}
