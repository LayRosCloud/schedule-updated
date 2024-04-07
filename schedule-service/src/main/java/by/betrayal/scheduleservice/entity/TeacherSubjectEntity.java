package by.betrayal.scheduleservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "teachers_subjects")
@Data
public class TeacherSubjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "teacher_id", nullable = false)
    private Long teacherId;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private SubjectEntity subject;
}
