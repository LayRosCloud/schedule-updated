package by.betrayal.audienceservice.core.utils.equals;

import by.betrayal.audienceservice.dto.audiencetype.AudienceTypeCreateDto;
import by.betrayal.audienceservice.dto.audiencetype.AudienceTypeFullDto;
import by.betrayal.audienceservice.dto.audiencetype.AudienceTypeUpdateDto;
import by.betrayal.audienceservice.entity.AudienceTypeEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public abstract class AudienceTypeEquals {

    public static void equalsEntityAndDto(AudienceTypeEntity item, AudienceTypeFullDto dto) {
        assertNotNull(item);
        assertNotNull(dto);

        assertEquals(item.getId(), dto.getId());
        assertEquals(item.getName(), dto.getName());
    }

    public static void equalsDto(AudienceTypeFullDto item, AudienceTypeCreateDto dto) {
        assertNotNull(item);
        assertNotNull(dto);

        assertEquals(item.getName(), dto.getName());
    }

    public static void equalsDto(AudienceTypeFullDto item, AudienceTypeUpdateDto dto) {
        assertNotNull(item);
        assertNotNull(dto);

        assertEquals(item.getId(), dto.getId());
        assertEquals(item.getName(), dto.getName());
    }
}