package by.betrayal.groupservice.repository;

import by.betrayal.groupservice.entity.CourseEntity;
import by.betrayal.groupservice.entity.GroupEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<GroupEntity, Long> {

    Page<GroupEntity> findAllByCourse(CourseEntity course, Pageable pageable);
}
