package by.betrayal.scheduleservice.repository;

import by.betrayal.scheduleservice.entity.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ClassRepository extends JpaRepository<ClassEntity, Long> {

    @Query(value = "SELECT class " +
                    "FROM ClassEntity class " +
                    "WHERE class.subgroup.groupId=?1 and class.dateStart <= ?3 and  class.dateEnd >= ?2", nativeQuery = false)
    List<ClassEntity> findAllBySubgroupId(Long subgroupId, Date start, Date end);


    @Query(value = "SELECT class " +
                    "FROM ClassEntity class " +
                    "WHERE class.teacherSubject.teacherId=?1 and class.dateStart <= ?3 and  class.dateEnd >= ?2", nativeQuery = false)
    List<ClassEntity> findAllByTeacherId(Long teacherId, Date start, Date end);

    @Query(value = "SELECT class " +
            "FROM ClassEntity class " +
            "WHERE class.audienceId=?1 and class.dateStart <= ?3 and  class.dateEnd >= ?2", nativeQuery = false)
    List<ClassEntity> findAllByAudienceId(Long audienceId, Date start, Date end);
}
