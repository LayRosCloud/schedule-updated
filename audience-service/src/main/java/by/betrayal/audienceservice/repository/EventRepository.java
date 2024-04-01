package by.betrayal.audienceservice.repository;

import by.betrayal.audienceservice.entity.AudienceEntity;
import by.betrayal.audienceservice.entity.CorpusEntity;
import by.betrayal.audienceservice.entity.EventEntity;
import by.betrayal.audienceservice.entity.InstitutionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {
    Page<EventEntity> findAllByAudience(AudienceEntity audience, Pageable pageable);
    List<EventEntity> findAllByAudience(AudienceEntity audience);
}
