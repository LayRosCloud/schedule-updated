package by.betrayal.groupservice.repository;

import by.betrayal.groupservice.entity.CourseEntity;
import by.betrayal.groupservice.entity.FacultyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<CourseEntity, Long> {

    Page<CourseEntity> findAllByFaculty(FacultyEntity faculty, Pageable pageable);
}
