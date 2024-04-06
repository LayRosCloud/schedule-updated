package by.betrayal.audienceservice.repository;

import by.betrayal.audienceservice.entity.AudienceTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AudienceTypeRepository extends JpaRepository<AudienceTypeEntity, Short> {
}
