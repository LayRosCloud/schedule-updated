package by.betrayal.personalservice.service;

import by.betrayal.personalservice.dto.person.PersonCreateDto;
import by.betrayal.personalservice.dto.person.PersonFullDto;

import java.util.List;

public interface PersonService {

    List<PersonFullDto> findAll();
    PersonFullDto findById(Long id);
    PersonFullDto create(PersonCreateDto dto);
}
