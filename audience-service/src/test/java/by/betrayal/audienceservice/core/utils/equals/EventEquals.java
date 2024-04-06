package by.betrayal.audienceservice.core.utils.equals;

import by.betrayal.audienceservice.dto.event.EventCreateDto;
import by.betrayal.audienceservice.dto.event.EventFullDto;
import by.betrayal.audienceservice.dto.event.EventUpdateDto;
import by.betrayal.audienceservice.entity.EventEntity;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public abstract class EventEquals {

    public static void equalsEntityAndDto(EventEntity item, EventFullDto dto) {
        assertNotNull(item);
        assertNotNull(dto);

        assertEquals(item.getId(), dto.getId());
        assertEquals(item.getValue(), dto.getValue());
        equalsDate(item.getDateEnd(), dto.getDateEnd());
        equalsDate(item.getDateStart(), dto.getDateStart());
        AudienceEquals.equalsEntityAndDto(item.getAudience(), dto.getAudience());
    }

    public static void equalsDto(EventFullDto item, EventCreateDto dto) {
        assertNotNull(item);
        assertNotNull(dto);

        assertEquals(item.getValue(), dto.getValue());
        equalsDate(item.getDateEnd(), dto.getDateEnd());
        equalsDate(item.getDateStart(), dto.getDateStart());
        assertEquals(item.getAudience().getId(), dto.getAudienceId());
    }

    public static void equalsDto(EventFullDto item, EventUpdateDto dto) {
        assertNotNull(item);
        assertNotNull(dto);

        assertEquals(item.getId(), dto.getId());
        assertEquals(item.getValue(), dto.getValue());
        equalsDate(item.getDateEnd(), dto.getDateEnd());
        equalsDate(item.getDateStart(), dto.getDateStart());
        assertEquals(item.getAudience().getId(), dto.getAudienceId());
    }

    private static void equalsDate(Date first, Date second) {
        assertNotNull(first);
        assertNotNull(second);

        assertEquals(first.getTime(), second.getTime());
    }
}
