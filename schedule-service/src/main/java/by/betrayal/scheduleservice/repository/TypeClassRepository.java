package by.betrayal.scheduleservice.repository;

import by.betrayal.scheduleservice.entity.TypeClassEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface TypeClassRepository extends JpaRepository<TypeClassEntity, Long> {
    List<TypeClassEntity> findAllByIdIn(Collection<Long> id);
    Page<TypeClassEntity> findAllByInstitutionId(Long institutionId, Pageable pageable);
    List<TypeClassEntity> findAllByInstitutionId(Long institutionId);
}
