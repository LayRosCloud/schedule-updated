package by.betrayal.scheduleservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Time;

@Entity
@Table(name = "times")
@Data
public class TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "time_start", nullable = false)
    private Time timeStart;

    @Column(name = "time_end", nullable = false)
    private Time timeEnd;
}
