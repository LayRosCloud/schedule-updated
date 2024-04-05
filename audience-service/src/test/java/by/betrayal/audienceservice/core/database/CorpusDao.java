package by.betrayal.audienceservice.core.database;

import by.betrayal.audienceservice.core.utils.generator.CorpusGenerator;
import by.betrayal.audienceservice.entity.CorpusEntity;
import by.betrayal.audienceservice.repository.CorpusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class CorpusDao {

    @Autowired
    private CorpusRepository repository;

    @Autowired
    private InstitutionDao institutionDao;

    @Transactional
    public void clearTable() {
        repository.deleteAll();
        institutionDao.clearTable();
    }

    @Transactional
    public CorpusEntity save() {
        var corpus = CorpusGenerator.generateCorpus();
        var item = institutionDao.save();
        corpus.setInstitution(item);
        return repository.save(corpus);
    }

    @Transactional
    public List<CorpusEntity> save(int count) {
        var list = new ArrayList<CorpusEntity>(count);
        var institution = institutionDao.save();
        for (int i = 0; i < count; i++) {
            var item = CorpusGenerator.generateCorpus();
            item.setInstitution(institution);
            list.add(repository.save(item));
        }
        return list;
    }
}