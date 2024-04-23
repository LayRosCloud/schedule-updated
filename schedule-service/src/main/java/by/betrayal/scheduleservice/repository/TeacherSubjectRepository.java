package by.betrayal.scheduleservice.repository;

import by.betrayal.scheduleservice.entity.TeacherSubjectEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface TeacherSubjectRepository extends JpaRepository<TeacherSubjectEntity, Long> {
    List<TeacherSubjectEntity> findAllByIdIn(Collection<Long> id);
    Page<TeacherSubjectEntity> findAllByTeacherId(Long teacherId, Pageable pageable);
    List<TeacherSubjectEntity> findAllByTeacherId(Long teacherId);
}
