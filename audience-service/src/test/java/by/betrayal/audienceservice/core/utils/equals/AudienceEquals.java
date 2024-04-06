package by.betrayal.audienceservice.core.utils.equals;

import by.betrayal.audienceservice.dto.audience.AudienceCreateDto;
import by.betrayal.audienceservice.dto.audience.AudienceFullDto;
import by.betrayal.audienceservice.dto.audience.AudienceUpdateDto;
import by.betrayal.audienceservice.entity.AudienceEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public abstract class AudienceEquals {

    public static void equalsEntityAndDto(AudienceEntity item, AudienceFullDto dto) {
        assertNotNull(item);
        assertNotNull(dto);

        assertEquals(item.getId(), dto.getId());
        assertEquals(item.getName(), dto.getName());
        CorpusEquals.equalsEntityAndDto(item.getCorpus(), dto.getCorpus());
        AudienceTypeEquals.equalsEntityAndDto(item.getType(), dto.getType());
    }

    public static void equalsDto(AudienceFullDto item, AudienceCreateDto dto) {
        assertNotNull(item);
        assertNotNull(dto);

        assertEquals(item.getName(), dto.getName());
        assertEquals(item.getCorpus().getId(), dto.getCorpusId());
        assertEquals(item.getType().getId(), dto.getTypeId());
    }

    public static void equalsDto(AudienceFullDto item, AudienceUpdateDto dto) {
        assertNotNull(item);
        assertNotNull(dto);

        assertEquals(item.getId(), dto.getId());
        assertEquals(item.getName(), dto.getName());
        assertEquals(item.getCorpus().getId(), dto.getCorpusId());
        assertEquals(item.getType().getId(), dto.getTypeId());
    }
}
