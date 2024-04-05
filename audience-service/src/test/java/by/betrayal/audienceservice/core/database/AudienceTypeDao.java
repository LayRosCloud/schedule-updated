package by.betrayal.audienceservice.core.database;

import by.betrayal.audienceservice.core.utils.generator.AudienceTypeGenerator;
import by.betrayal.audienceservice.entity.AudienceTypeEntity;
import by.betrayal.audienceservice.repository.AudienceTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class AudienceTypeDao {

    @Autowired
    private AudienceTypeRepository repository;

    @Transactional
    public void clearTable() {
        repository.deleteAll();
    }

    @Transactional
    public AudienceTypeEntity save() {
        return repository.save(AudienceTypeGenerator.generateType());
    }

    @Transactional
    public List<AudienceTypeEntity> save(int count) {
        var list = AudienceTypeGenerator.generateTypes(count);
        return repository.saveAll(list);
    }
}
