package by.betrayal.audienceservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "audiences")
@Data
public class AudienceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "corpus_id", nullable = false)
    private CorpusEntity corpus;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private AudienceTypeEntity type;
}
