package by.betrayal.scheduleservice.repository;

import by.betrayal.scheduleservice.entity.TypeClassEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface TypeClassRepository extends JpaRepository<TypeClassEntity, Long> {
    List<TypeClassEntity> findAllByIdIn(Collection<Long> id);
    Page<TypeClassEntity> findAllByInstitutionId(Long institutionId, Pageable pageable);
    List<TypeClassEntity> findAllByInstitutionId(Long institutionId);
}
