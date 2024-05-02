package by.betrayal.requestservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "messages")
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "created_at", nullable = false)
    private Long createdAt;

    @Column(name = "updated_at", nullable = false)
    private Long updatedAt;

    @ManyToOne
    @JoinColumn(name = "participant_id", nullable = false)
    private ParticipantEntity participant;

    @Enumerated
    @Column(name = "type", nullable = false)
    private TypeMessage type;
}
