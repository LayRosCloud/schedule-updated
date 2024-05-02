package by.betrayal.requestservice.repository;

import by.betrayal.requestservice.entity.ParticipantEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<ParticipantEntity, Long> {
    Page<ParticipantEntity> findAllByPersonId(Long personId, Pageable pageable);
}
