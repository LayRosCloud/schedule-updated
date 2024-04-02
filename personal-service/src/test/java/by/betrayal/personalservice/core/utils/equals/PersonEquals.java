package by.betrayal.personalservice.core.utils.equals;

import by.betrayal.personalservice.dto.person.PersonCreateDto;
import by.betrayal.personalservice.dto.person.PersonFullDto;
import by.betrayal.personalservice.dto.person.PersonUpdateDto;
import by.betrayal.personalservice.entity.PersonEntity;

import static org.junit.jupiter.api.Assertions.*;

public class PersonEquals {

    public static void equalsEntity(PersonEntity first, PersonEntity second) {
        assertNotNull(first);
        assertNotNull(second);

        assertEquals(first.getId(), second.getId());
        assertEquals(first.getFirstName(), second.getFirstName());
        assertEquals(first.getLastName(), second.getLastName());
        assertEquals(first.getPatronymic(), second.getPatronymic());
        assertEquals(first.getPhoto(), second.getPhoto());
        assertEquals(first.getInstitutionId(), second.getInstitutionId());
    }

    public static void equalsEntityAndDto(PersonEntity item, PersonFullDto dto) {
        assertNotNull(item);
        assertNotNull(dto);

        assertEquals(item.getId(), dto.getId());
        assertEquals(item.getFirstName(), dto.getFirstName());
        assertEquals(item.getLastName(), dto.getLastName());
        assertEquals(item.getPatronymic(), dto.getPatronymic());
        assertEquals(item.getPhoto(), dto.getPhoto());
        assertEquals(item.getInstitutionId(), dto.getInstitutionId());
    }

    public static void equalsDto(PersonFullDto item, PersonCreateDto dto) {
        assertNotNull(item);
        assertNotNull(dto);

        assertEquals(item.getFirstName(), dto.getFirstName());
        assertEquals(item.getLastName(), dto.getLastName());
        assertEquals(item.getPatronymic(), dto.getPatronymic());
        assertEquals(item.getInstitutionId(), dto.getInstitutionId());
    }

    public static void equalsDto(PersonFullDto item, PersonUpdateDto dto) {
        assertNotNull(item);
        assertNotNull(dto);

        assertEquals(item.getId(), dto.getId());
        assertEquals(item.getFirstName(), dto.getFirstName());
        assertEquals(item.getLastName(), dto.getLastName());
        assertEquals(item.getPatronymic(), dto.getPatronymic());
        assertEquals(item.getInstitutionId(), dto.getInstitutionId());
    }
}
