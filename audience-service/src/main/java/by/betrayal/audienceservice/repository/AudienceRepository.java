package by.betrayal.audienceservice.repository;

import by.betrayal.audienceservice.entity.AudienceEntity;
import by.betrayal.audienceservice.entity.CorpusEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AudienceRepository extends JpaRepository<AudienceEntity, Long> {
    List<AudienceEntity> findAllByCorpus(CorpusEntity corpus);
    Page<AudienceEntity> findAllByCorpus(CorpusEntity corpus, Pageable pageable);
}
