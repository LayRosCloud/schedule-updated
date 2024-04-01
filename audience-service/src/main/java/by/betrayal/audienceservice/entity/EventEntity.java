package by.betrayal.audienceservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "events")
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "audience_id")
    private AudienceEntity audience;

    @Column(name = "value", nullable = false)
    private String value;

    @Column(name = "date_start", nullable = false)
    private Date dateStart;

    @Column(name = "date_end",nullable = false)
    private Date dateEnd;
}
