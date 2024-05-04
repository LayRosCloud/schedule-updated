package by.betrayal.scheduleservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "classes")
@Data
public class ClassEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_start", nullable = false)
    private Date dateStart;

    @Column(name = "date_end", nullable = false)
    private Date dateEnd;

    @Column(name = "audience_id", nullable = false)
    private Long audienceId;

    @ManyToOne
    @JoinColumn(name = "teacher_subject_id", nullable = false)
    private TeacherSubjectEntity teacherSubject;

    @ManyToOne
    @JoinColumn(name = "subgroup_id", nullable = false)
    private SubgroupEntity subgroup;

    @ManyToOne
    @JoinColumn(name = "time_id", nullable = false)
    private TimeEntity time;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private TypeClassEntity type;


}
