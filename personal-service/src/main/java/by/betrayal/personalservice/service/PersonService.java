package by.betrayal.personalservice.service;

import by.betrayal.personalservice.dto.person.PersonCreateDto;
import by.betrayal.personalservice.dto.person.PersonFullDto;
import by.betrayal.personalservice.dto.person.PersonUpdateDto;
import by.betrayal.personalservice.utils.pageable.PageContainer;
import by.betrayal.personalservice.utils.pageable.PageOptions;

import java.util.List;

public interface PersonService {

    PageContainer<PersonFullDto> findAll(PageOptions options);
    List<PersonFullDto> findAll();
    PersonFullDto findById(Long id);
    PersonFullDto create(PersonCreateDto dto);
    PersonFullDto update(PersonUpdateDto dto);
    PersonFullDto delete(Long id);
}
