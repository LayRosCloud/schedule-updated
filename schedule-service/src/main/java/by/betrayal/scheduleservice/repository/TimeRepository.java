package by.betrayal.scheduleservice.repository;

import by.betrayal.scheduleservice.entity.TimeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface TimeRepository extends JpaRepository<TimeEntity, Long> {
    List<TimeEntity> findAllByIdIn(Collection<Long> id);
    Page<TimeEntity> findAllByInstitutionId(Long institutionId, Pageable pageable);
    List<TimeEntity> findAllByInstitutionId(Long institutionId);
}
