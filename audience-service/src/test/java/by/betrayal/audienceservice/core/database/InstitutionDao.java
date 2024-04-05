package by.betrayal.audienceservice.core.database;

import by.betrayal.audienceservice.core.utils.generator.InstitutionGenerator;
import by.betrayal.audienceservice.entity.InstitutionEntity;
import by.betrayal.audienceservice.repository.InstitutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class InstitutionDao {

    @Autowired
    private InstitutionRepository repository;

    @Transactional
    public void clearTable() {
        repository.deleteAll();
    }

    @Transactional
    public InstitutionEntity save() {
        return repository.save(InstitutionGenerator.generateInstitution());
    }

    @Transactional
    public List<InstitutionEntity> save(int count) {
        var list = InstitutionGenerator.generateInstitutionList(count);
        return repository.saveAll(list);
    }
}
