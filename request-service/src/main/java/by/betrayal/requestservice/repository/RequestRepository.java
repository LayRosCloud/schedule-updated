package by.betrayal.requestservice.repository;

import by.betrayal.requestservice.entity.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<RequestEntity, Long> {
}
