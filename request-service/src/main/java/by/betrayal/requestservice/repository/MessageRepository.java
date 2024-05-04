package by.betrayal.requestservice.repository;

import by.betrayal.requestservice.entity.MessageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Long> {
    @Query(value = "SELECT mes.id, mes.createdAt, mes.updatedAt, mes.text, mes.type, mes.participant " +
            "FROM MessageEntity mes " +
            "WHERE mes.participant.request.id = ?1",
            nativeQuery = false)
    Page<MessageEntity> findAllByRequestId(Long requestId, Pageable pageable);
}
