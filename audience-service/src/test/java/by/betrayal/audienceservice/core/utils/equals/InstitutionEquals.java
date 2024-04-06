package by.betrayal.audienceservice.core.utils.equals;

import by.betrayal.audienceservice.dto.institution.InstitutionCreateDto;
import by.betrayal.audienceservice.dto.institution.InstitutionFullDto;
import by.betrayal.audienceservice.dto.institution.InstitutionUpdateDto;
import by.betrayal.audienceservice.entity.InstitutionEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public abstract class InstitutionEquals {

    public static void equalsEntityAndDto(InstitutionEntity item, InstitutionFullDto dto) {
        assertNotNull(item);
        assertNotNull(dto);

        assertEquals(item.getId(), dto.getId());
        assertEquals(item.getName(), dto.getName());
    }

    public static void equalsDto(InstitutionFullDto item, InstitutionCreateDto dto) {
        assertNotNull(item);
        assertNotNull(dto);

        assertEquals(item.getName(), dto.getName());
    }

    public static void equalsDto(InstitutionFullDto item, InstitutionUpdateDto dto) {
        assertNotNull(item);
        assertNotNull(dto);

        assertEquals(item.getId(), dto.getId());
        assertEquals(item.getName(), dto.getName());
    }
}
