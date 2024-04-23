package by.betrayal.scheduleservice.repository;

import by.betrayal.scheduleservice.entity.SubgroupEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface SubgroupRepository extends JpaRepository<SubgroupEntity, Long> {
    List<SubgroupEntity> findAllByIdIn(Collection<Long> id);
    Page<SubgroupEntity> findAllByGroupId(Long groupId, Pageable pageable);
    List<SubgroupEntity> findAllByGroupId(Long groupId);
}
