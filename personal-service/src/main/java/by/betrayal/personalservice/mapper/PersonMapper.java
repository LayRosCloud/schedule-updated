package by.betrayal.personalservice.mapper;

import by.betrayal.personalservice.dto.person.PersonCreateDto;
import by.betrayal.personalservice.dto.person.PersonFullDto;
import by.betrayal.personalservice.dto.person.PersonUpdateDto;
import by.betrayal.personalservice.entity.PersonEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    List<PersonFullDto> mapToFullDto(List<PersonEntity> persons);
    PersonFullDto mapToFullDto(PersonEntity person);

    @Mapping(target = "photo", ignore = true)
    @Mapping(target = "id", ignore = true)
    PersonEntity mapToEntity(PersonCreateDto dto);

    default void mapToEntity(PersonEntity person, PersonUpdateDto dto) {
        person.setFirstName(dto.getFirstName());
        person.setLastName(dto.getLastName());
        person.setPatronymic(dto.getPatronymic());
        person.setInstitutionId(dto.getInstitutionId());
    }
}
