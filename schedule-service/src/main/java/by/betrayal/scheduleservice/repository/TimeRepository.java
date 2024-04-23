package by.betrayal.scheduleservice.repository;

import by.betrayal.scheduleservice.entity.TimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface TimeRepository extends JpaRepository<TimeEntity, Long> {
    List<TimeEntity> findAllByIdIn(Collection<Long> id);
}
