package by.betrayal.scheduleservice.repository;

import by.betrayal.scheduleservice.entity.TimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeRepository extends JpaRepository<TimeEntity, Long> {
}
