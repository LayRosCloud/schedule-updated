package by.betrayal.scheduleservice.repository;

import by.betrayal.scheduleservice.entity.SubjectEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<SubjectEntity, Long> {
    Page<SubjectEntity> findAllByInstitutionId(Long institutionId, Pageable pageable);
}
