package by.betrayal.scheduleservice.repository;

import by.betrayal.scheduleservice.entity.TeacherSubjectEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface TeacherSubjectRepository extends JpaRepository<TeacherSubjectEntity, Long> {
    List<TeacherSubjectEntity> findAllByIdIn(Collection<Long> id);
    Page<TeacherSubjectEntity> findAllByTeacherId(Long teacherId, Pageable pageable);
    List<TeacherSubjectEntity> findAllByTeacherId(Long teacherId);
}
