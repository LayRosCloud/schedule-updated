package by.betrayal.personalservice.core.database;

import by.betrayal.personalservice.core.utils.generator.PersonGenerator;
import by.betrayal.personalservice.entity.PersonEntity;
import by.betrayal.personalservice.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class PersonDao {

    @Autowired
    private PersonRepository repository;

    @Transactional
    public void clearTable() {
        repository.deleteAll();
    }

    @Transactional
    public PersonEntity save() {
        return repository.save(PersonGenerator.generatePerson());
    }

    @Transactional
    public List<PersonEntity> saveArray(Integer count) {
        var list = PersonGenerator.generatePersons(count);
        return repository.saveAll(list);
    }

}
