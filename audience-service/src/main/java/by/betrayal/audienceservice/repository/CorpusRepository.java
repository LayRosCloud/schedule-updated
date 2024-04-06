package by.betrayal.audienceservice.repository;

import by.betrayal.audienceservice.entity.CorpusEntity;
import by.betrayal.audienceservice.entity.InstitutionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CorpusRepository extends JpaRepository<CorpusEntity, Long> {
    Page<CorpusEntity> findAllByInstitution(InstitutionEntity institution, Pageable pageable);
    List<CorpusEntity> findAllByInstitution(InstitutionEntity institution);
}
