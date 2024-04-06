package by.betrayal.audienceservice.core.utils.equals;

import by.betrayal.audienceservice.dto.corpus.CorpusCreateDto;
import by.betrayal.audienceservice.dto.corpus.CorpusFullDto;
import by.betrayal.audienceservice.dto.corpus.CorpusUpdateDto;
import by.betrayal.audienceservice.entity.CorpusEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public abstract class CorpusEquals {

    public static void equalsEntityAndDto(CorpusEntity item, CorpusFullDto dto) {
        assertNotNull(item);
        assertNotNull(dto);

        assertEquals(item.getId(), dto.getId());
        assertEquals(item.getName(), dto.getName());
        InstitutionEquals.equalsEntityAndDto(item.getInstitution(), dto.getInstitution());
    }

    public static void equalsDto(CorpusFullDto item, CorpusCreateDto dto) {
        assertNotNull(item);
        assertNotNull(dto);

        assertEquals(item.getName(), dto.getName());
        assertEquals(item.getInstitution().getId(), dto.getInstitutionId());
    }

    public static void equalsDto(CorpusFullDto item, CorpusUpdateDto dto) {
        assertNotNull(item);
        assertNotNull(dto);

        assertEquals(item.getId(), dto.getId());
        assertEquals(item.getName(), dto.getName());
        assertEquals(item.getInstitution().getId(), dto.getInstitutionId());
    }
}
