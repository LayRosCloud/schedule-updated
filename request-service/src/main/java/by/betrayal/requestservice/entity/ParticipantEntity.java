package by.betrayal.requestservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "participants")
public class ParticipantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "person_id", nullable = false)
    private Long personId;

    @ManyToOne
    @JoinColumn(name = "request_id", nullable = false)
    private RequestEntity request;

    @Column(name = "deleted_at", nullable = true)
    private Long deletedAt;

    @Column(name = "is_hidden", nullable = false)
    private Boolean isHidden;
}
