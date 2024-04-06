package by.betrayal.audienceservice.core.database;

import by.betrayal.audienceservice.core.utils.generator.AudienceGenerator;
import by.betrayal.audienceservice.entity.AudienceEntity;
import by.betrayal.audienceservice.repository.AudienceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class AudienceDao {

    @Autowired
    private AudienceRepository repository;

    @Autowired
    private CorpusDao corpusDao;

    @Autowired
    private AudienceTypeDao audienceTypeDao;

    @Transactional
    public void clearTable() {
        repository.deleteAll();
        corpusDao.clearTable();
    }

    @Transactional
    public AudienceEntity save() {
        var item = AudienceGenerator.generateAudience();
        var corpus = corpusDao.save();
        var type = audienceTypeDao.save();
        item.setCorpus(corpus);
        item.setType(type);
        return repository.save(item);
    }

    @Transactional
    public List<AudienceEntity> save(int count) {
        var list = new ArrayList<AudienceEntity>(count);
        var corpus = corpusDao.save();
        var type = audienceTypeDao.save();
        for (int i = 0; i < count; i++) {
            var item = AudienceGenerator.generateAudience();
            item.setType(type);
            item.setCorpus(corpus);
            list.add(repository.save(item));
        }
        return list;
    }
}
