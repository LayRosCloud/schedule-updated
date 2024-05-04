package by.betrayal.groupservice.repository;

import by.betrayal.groupservice.entity.FacultyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface FacultyRepository extends JpaRepository<FacultyEntity, Long> {

    Page<FacultyEntity> findAllByCorpusId(Long corpusId, Pageable pageable);
    List<FacultyEntity> findAllByIdIn(Collection<Long> ids);
}
