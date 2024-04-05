package by.betrayal.audienceservice.core.database;

import by.betrayal.audienceservice.core.utils.generator.EventGenerator;
import by.betrayal.audienceservice.entity.EventEntity;
import by.betrayal.audienceservice.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class EventDao {

    @Autowired
    private EventRepository repository;

    @Autowired
    private AudienceDao audienceDao;

    @Transactional
    public void clearTable() {
        repository.deleteAll();
        audienceDao.clearTable();
    }

    @Transactional
    public EventEntity save() {
        var item = EventGenerator.generateEvent();
        var audience = audienceDao.save();
        item.setAudience(audience);
        return repository.save(item);
    }

    @Transactional
    public List<EventEntity> save(int count) {
        var list = new ArrayList<EventEntity>(count);
        var audience = audienceDao.save();
        for (int i = 0; i < count; i++) {
            var item = EventGenerator.generateEvent();
            item.setAudience(audience);
            list.add(repository.save(item));
        }
        return list;
    }
}
