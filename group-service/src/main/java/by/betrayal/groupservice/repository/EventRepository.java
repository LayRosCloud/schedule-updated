package by.betrayal.groupservice.repository;

import by.betrayal.groupservice.entity.EventEntity;
import by.betrayal.groupservice.entity.GroupEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {

    Page<EventEntity> findAllByGroup(GroupEntity group, Pageable pageable);
}
